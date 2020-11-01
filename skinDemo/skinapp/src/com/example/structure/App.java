package com.example.structure;

import android.app.Application;

import com.example.skinlibrary.SkinManager;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/19
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);

        //根据app上次推出的状态来判断是否需要设置夜间模式,提前在
        //sharedPreference中存了一个是否是夜间模式的布尔值

//        //日间切换夜间
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//        //夜间切换日间
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }
}
