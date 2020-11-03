package com.example.router_compiler.processor;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

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
//@SupportedAnnotationTypes()
//@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouteProcessor extends AbstractProcessor {
    //javapoet 代码生成器

    //初始化工作
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    //指定使用的Java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //注册给哪些注解的
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        return false;
    }
}
