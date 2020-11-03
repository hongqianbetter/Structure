package com.example.router_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/3
 */
@Target(ElementType.TYPE) //作用到类上面
@Retention(RetentionPolicy.CLASS) //注解存在到编译期
public @interface Route {

    //表示路由节点
    String path();

    //将路由节点进分组,可以实现按组动态加载
    String group() default "";

}
