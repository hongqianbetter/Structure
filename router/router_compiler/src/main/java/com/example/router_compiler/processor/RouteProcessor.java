package com.example.router_compiler.processor;

import com.example.router_annotation.Route;
import com.example.router_annotation.model.RouteMeta;
import com.example.router_compiler.utils.Consts;
import com.example.router_compiler.utils.Log;
import com.example.router_compiler.utils.Utils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/3
 * 注解处理器 扫描到注解 会走这个方法
 * 在这个类上添加了@AutoService注解 ,他的作用是用来生成META-INF/services/javax.annation.processing.Processor
 * 文件的.
 * 也就是说我们在使用注解处理器的时候需要手动添加 上面那个文件 而有了 AutoService后 他会帮我们自动生成
 * AutoService是Google开发的一个库 ,使用时需要在factory-compiler中添加依赖
 */

@AutoService(Process.class)
@SupportedAnnotationTypes(Consts.ANN_TYPE_ROUTE)
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouteProcessor extends AbstractProcessor {
    //javapoet 代码生成器
    //文件生成工具
    private Filer filerUtils;
    private Log log;
    //(类信息工具)
    private Types typesUtils;

    //节点工具类 (类属性函数都是节点)
    private Elements elementsUtils;

    //分组 key:组名 value 对应的路由信息
    private Map<String, List<RouteMeta>> groupMap = new HashMap<>();

    //初始化工作
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        log = Log.newLog(processingEnvironment.getMessager());
        filerUtils = processingEnvironment.getFiler();
        typesUtils = processingEnvironment.getTypeUtils();
        elementsUtils = processingEnvironment.getElementUtils();
    }

    //指定使用的Java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //    //注册给哪些注解的
    //    @Override
    //    public Set<String> getSupportedAnnotationTypes() {
    //
    //        return super.getSupportedAnnotationTypes();
    //    }

    // 相当于main函数 正式处理注解
    //set 使用了支持处理注解 的节点集合
    //roundEnvironment 表示当前或是之前的运行环境 可以通过该对象查找找到的注释
    //表示 后续处理器不会再处理 (已经处理)
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //使用了需要处理的注解
        if (!Utils.isEmpty(set)) {
            //获取所有被Route 注释的元素集合
            Set<? extends Element> with = roundEnvironment.getElementsAnnotatedWith(Route.class);
            //处理Route注解
            if (!Utils.isEmpty(with)) {
                log.i("Route Class : ==" + with.size());
                parseRoute(with);
            }

            return true;
        }


        return false;
    }

    private void parseRoute(Set<? extends Element> with) {
        //elementUtils 通过节点工具,传入全类名 ,生成节点
        TypeElement typeElement = elementsUtils.getTypeElement(Consts.ACTIVITY);
        //节点的描述信息
        TypeMirror type_activity = typeElement.asType();
        for (Element element : with) {
            RouteMeta routeMeta;
            //使用Route注解的类的描述信息
            TypeMirror mirror = element.asType();

            Route route = element.getAnnotation(Route.class);

            //判断注解用在什么类上面
            if (typesUtils.isSubtype(mirror, type_activity)) {
                routeMeta = new RouteMeta(RouteMeta.Type.ACTIVITY, route, element);
            } else {
                throw new RuntimeException("[Just Support Activity/Service] :" + element);
            }
            //路由信息的记录
            categories(routeMeta);
        }
        TypeElement iRouteGroup = elementsUtils.getTypeElement(Consts.IROUTE_GROUP);

        generateGroup(iRouteGroup);
    }

    private void generateGroup(TypeElement iRouteGroup) {
        ParameterizedTypeName atlas = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(RouteMeta.class)
        );
        //创建我们参数名字  Map<String,RouteMeta> atlas
        ParameterSpec spec = ParameterSpec.builder(atlas, "atlas").build();

        //遍历分组 每一个分组创建一个 ...$$$Group$$$类
        for (Map.Entry<String, List<RouteMeta>> stringListEntry : groupMap.entrySet()) {
            MethodSpec.Builder builder = MethodSpec.methodBuilder(Consts.METHOD_LOAD_INTO)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(spec);

            String groupName = stringListEntry.getKey();
            List<RouteMeta> groupData = stringListEntry.getValue();
            for (RouteMeta routeMeta : groupData) {
                builder.addStatement("atlas.put($S,$T.build($T.$L,$T.class,$S,$S)",
                        routeMeta.getPath(),
                        ClassName.get(RouteMeta.class),
                        ClassName.get(RouteMeta.Type.class),
                        routeMeta.getType(),
                        ClassName.get((TypeElement) routeMeta.getElement()),
                        routeMeta.getPath().toLowerCase(),
                        routeMeta.getGroup().toLowerCase());

            }
            String groupClassName = Consts.NAME_OF_GROUP + groupName;
            try {
                JavaFile.builder(Consts.PACKAGE_OF_GENERATE_FILE,
                        TypeSpec.classBuilder(groupClassName)
                                .addSuperinterface(ClassName.get(iRouteGroup))
                                .addModifiers(Modifier.PUBLIC)
                                .addMethod(builder.build()).build()).build().writeTo(filerUtils);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void categories(RouteMeta routeMeta) {
        if (routeVerify(routeMeta)) {
            List<RouteMeta> routeMetas = groupMap.get(routeMeta.getGroup());
            //如果未记录分组 则创建
            if (Utils.isEmpty(routeMetas)) {
                ArrayList<RouteMeta> routeMetaList = new ArrayList<>();
                routeMetaList.add(routeMeta);
                groupMap.put(routeMeta.getGroup(), routeMetaList);
            } else {
                routeMetas.add(routeMeta);
            }
        } else {
            log.i("Group Info Error :" + routeMeta.getPath());
        }
    }

    private boolean routeVerify(RouteMeta routeMeta) {
        String path = routeMeta.getPath();
        String group = routeMeta.getGroup();
        if (Utils.isEmpty(path) || !path.startsWith("/")) {
            return false;
        }

        if (Utils.isEmpty(group)) {
            String subStr = path.substring(1, path.indexOf("/", 1));
            if (Utils.isEmpty(subStr)) {
                return false;
            }
            routeMeta.setGroup(subStr);
            return true;
        }

        return true;
    }
}
