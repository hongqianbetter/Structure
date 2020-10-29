package com.example.skinlibrary;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;


import java.lang.reflect.Method;
import java.util.Observable;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/19
 */
public class SkinManager extends Observable {
    private static SkinManager instance;

    private Application app;

    public SkinManager(Application app) {
        this.app = app;
        //共享首选项 用于记录当前使用的皮肤
        SkinPreference.init(app);
        //资源管理类 用于从app/皮肤中加载资源
        SkinResources.init(app);
        app.registerActivityLifecycleCallbacks(new SkinActivityLifeCircle());
        loadSkin(SkinPreference.getInstance().getSkin());
    }

    public static SkinManager getInstance() {
        return instance;
    }

    public void loadSkin(String skinPath) { //设置是用app的Resources加载,还是插件app的Resource加载
        if (TextUtils.isEmpty(skinPath)) {
            //记录使用默认皮肤
            SkinPreference.getInstance().setSkin("");
            //清空资源管理器 皮肤资源属性
            SkinResources.getInstance().reset();
        } else {
            AssetManager manager = null;
            try {
                manager = AssetManager.class.newInstance(); //重新创建了一个AssetManager
//                AssetManager的addAssetPath负责将另一个apk的资源文件加载进当前应用，这里由于是api隐藏方法，采用反射方式调用。
//                查看addAssetPath方法注释，允许传递的路径为资源目录或者zip文件
                Method method = manager.getClass().getMethod("addAssetPath", String.class);
                method.invoke(manager, skinPath);
                Resources appResources = this.app.getResources();

                Resources skinResources = new Resources(manager, appResources.getDisplayMetrics(), appResources.getConfiguration());
                //记录
                SkinPreference.getInstance().setSkin(skinPath);
                //获取外部apk的包名
                PackageManager packageManager = this.app.getPackageManager();
                PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
                String packageName = packageArchiveInfo.packageName;
                SkinResources.getInstance().applySkin(skinResources, packageName);

            } catch (Exception e) {
                e.printStackTrace();
            }

            setChanged();
            notifyObservers();
        }
    }

    public static void init(Application app) {
        synchronized (SkinManager.class) {
            if (null == instance) {
                instance = new SkinManager(app);
            }
        }
    }



}
