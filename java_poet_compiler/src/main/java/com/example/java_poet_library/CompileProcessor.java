package com.example.java_poet_library;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;




/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/9
 */

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"java.lang.Override"})
public class CompileProcessor extends AbstractProcessor {

    Filer mFiler = null;
    private Log log;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        log = Log.newLog(processingEnvironment.getMessager());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        makeC();
        return true;
    }

    private void makeC() {
        ClassName activity = ClassName.get("android.app", "Activity");


        //参数相关
        ClassName bundle = ClassName.get("android.os", "Bundle");
        ParameterSpec param = ParameterSpec.builder(bundle, "savedInstanceState")
                .build();

        ClassName override = ClassName.get("java.lang", "Override");

        MethodSpec onCreateMethod = MethodSpec.methodBuilder("onCreate")
                .addAnnotation(override)
                                .addModifiers(Modifier.PROTECTED)
                .addParameter(param)
                .addStatement("super.onCreate(savedInstanceState)")
                .addStatement("setContentView(R.layout.activity_main)")
                .build();


        TypeSpec mainActivity = TypeSpec.classBuilder("MainActivity")
                                .addModifiers(Modifier.PUBLIC)
                .superclass(activity)
                .addMethod(onCreateMethod)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.java_poet", mainActivity)
                .build();
        log.i("000000000000000000000000000000000000");

//        然后编译，在app的build/generated/source/apt目录下，可看到生成的代码
        try {
            javaFile.writeTo(mFiler);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
