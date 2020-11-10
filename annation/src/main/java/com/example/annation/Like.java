package com.example.annation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/9
 */
@Target(ElementType.TYPE) //作用到类上面
@Retention(RetentionPolicy.CLASS) //注解存在到编译期
public @interface Like {
}
