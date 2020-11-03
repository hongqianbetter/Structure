package com.example.router_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/3
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Extra {

   String name() default "";

}
