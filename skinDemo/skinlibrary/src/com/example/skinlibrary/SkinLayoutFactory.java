package com.example.skinlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/19
 */
public class SkinLayoutFactory implements LayoutInflater.Factory2, Observer {
    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    private static final Class[] mConstructureSignature =
            new Class[]{Context.class, AttributeSet.class};

    private static final HashMap<String
            , Constructor<? extends View>> mConstructure =
            new HashMap<>();

    private SkinAttribute skinAttribute;

    Activity activity;

    public SkinLayoutFactory(Activity activity, Typeface typeface) {
        this.skinAttribute = new SkinAttribute(typeface);
        this.activity=activity;

    }


    //为什么重写四个参数的
//    if(mFactory2 !=null)
//    {
//        view = mFactory2.onCreateView(parent, name, context, attrs);
//    } else if(mFactory !=null)
//    {
//        view = mFactory.onCreateView(name, context, attrs);
//    }


    @Nullable
    @Override//    系统的createViewFromTag 每次创建View都会调用,这个方法里面调用了Factory2.onCreateView的方法
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        //创建系统View对象
        View view = createViewFromTag(name, context, attrs);

        //筛选符合的View
        skinAttribute.load(view, attrs);

        return view;
    }


    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        if (-1 != name.indexOf(".")) { //如果是TextView 这样写 是木有.的  说明肯定是系统的View
            return createView(name, context, attrs);//        //自定义view 或者全类名的系统View
        }
        View view = null;
        for (int i = 0; i < mClassPrefixList.length; i++) {
            view = createView(mClassPrefixList[i] + name, context, attrs);
            if (view != null) {
                break;
            }
        }
        return view;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = mConstructure.get(name);
        if (constructor == null) {
            try {
                Class<? extends View> aClass = context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = aClass.getConstructor(mConstructureSignature);
                mConstructure.put(name, constructor);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        if (null != constructor) {
            try {
                return constructor.newInstance(context, attrs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

        Typeface skinTypeface = SkinThemeUtils.getSkinTypeface(activity);
        skinAttribute.setTypeface(skinTypeface);
        //更换皮肤
        skinAttribute.applySkin();

        //替换状态栏
        SkinThemeUtils.updateStatusBarColor(activity);
    }
}
