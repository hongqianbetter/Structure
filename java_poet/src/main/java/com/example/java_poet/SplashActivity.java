package com.example.java_poet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.annation.Like;


@Like
public class SplashActivity extends AppCompatActivity {

//    TypeSpec————用于生成类、接口、枚举对象的类
//    MethodSpec————用于生成方法对象的类
//    ParameterSpec————用于生成参数对象的类
//    AnnotationSpec————用于生成注解对象的类
//    FieldSpec————用于配置生成成员变量的类
//    ClassName————通过包名和类名生成的对象，在JavaPoet中相当于为其指定Class
//    ParameterizedTypeName————通过MainClass和IncludeClass生成包含泛型的Class
//    JavaFile————控制生成的Java文件的输出的类

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ClassName activity = ClassName.get("android.app", "Activity");
//
//
//
//
//        //参数相关
//        ClassName bundle = ClassName.get("android.os", "Bundle");
//        ParameterSpec param = ParameterSpec.builder(bundle, "savedInstanceState")
//                .build();
//
//        ClassName override = ClassName.get("java.lang", "Override");
//
//        MethodSpec onCreateMethod = MethodSpec.methodBuilder("onCreate")
//                .addAnnotation(override)
////                .addModifiers(Modifier.PROTECTED)
//                .addParameter(param)
//                .addStatement("super.onCreate(savedInstanceState)")
//                .addStatement("setContentView(R.layout.activity_main)")
//                .build();
//
//
//        TypeSpec mainActivity = TypeSpec.classBuilder("MainActivity")
////                .addModifiers(Modifier.PUBLIC)
//                .superclass(activity)
//                .addMethod(onCreateMethod)
//                .build();
//
//        JavaFile file = JavaFile.builder("com.example.java_poet", mainActivity)
//                .build();
//
//        try {
//            file.writeTo(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
