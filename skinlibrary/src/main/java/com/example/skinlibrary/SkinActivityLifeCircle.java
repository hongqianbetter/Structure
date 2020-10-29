package com.example.skinlibrary;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/19
 */
public class SkinActivityLifeCircle implements Application.ActivityLifecycleCallbacks {
    HashMap<Activity, SkinLayoutFactory> factoryHashMap = new HashMap<>();


    @Override //在Activity的super.onCreate之前调用
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.e("JJJ",activity.getResources().toString()+"---"+activity.getResources().getAssets().toString()+" "+activity.toString());
      //更新状态栏
        SkinThemeUtils.updateStatusBarColor(activity);
      //更新字体
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);

        //不同上下文获得的LayoutInflater是不一样的
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        try {
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.setBoolean(layoutInflater, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建自定义View工厂
        SkinLayoutFactory factory = new SkinLayoutFactory(activity,typeface);
        layoutInflater.setFactory2(factory);
        //注册观察者
        SkinManager.getInstance().addObserver(factory);
        factoryHashMap.put(activity, factory);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        SkinLayoutFactory factory = factoryHashMap.remove(activity);
        SkinManager.getInstance().deleteObserver(factory);
    }
}
