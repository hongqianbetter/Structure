package com.example.skinlibrary;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

public class SkinResources {

    private static SkinResources instance;

    private Resources mSkinResources;
    private String mSkinPkgName;
    private boolean isDefaultSkin = true;

    private Resources mAppResources;

    private SkinResources(Context context) {
        mAppResources = context.getResources();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResources.class) {
                if (instance == null) {
                    instance = new SkinResources(context);
                }
            }
        }
    }

    public static SkinResources getInstance() {
        return instance;
    }

    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }

    public void applySkin(Resources resources, String pkgName) {
        mSkinResources = resources;
        mSkinPkgName = pkgName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }


    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        // 有些时候资源的id想要不通过R.layout.xxx获取Id，这时候就可以使用Resources.getIdentifier()
        // 这个方法来获取。  通过在app中的id 获取资源的类型,名称  再向皮肤包中获取id
        //获取对应id 在当前的名称 colorPrimary       R.color.white
        String resName = mAppResources.getResourceEntryName(resId); //返回white
        String resType = mAppResources.getResourceTypeName(resId); //返回color
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName); //获取资源在 皮肤包中的id
        return skinId;
    }

    public int getColor(int resId) { // 根据app的id 进行转换 获得插件的Id
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }

    public Drawable getDrawable(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }


    /**
     * 可能是Color 也可能是drawable
     *
     * @return
     */
    public Object getBackground(int resId) {  //根据Id获取资源的类型 color或者drawable
        String resourceTypeName = mAppResources.getResourceTypeName(resId);

        if (resourceTypeName.equals("color")) {
            return getColor(resId);
        } else {
            // drawable
            return getDrawable(resId);
        }
    }

    public String getString(int resId) {
        try {
            if (isDefaultSkin) {
                return mAppResources.getString(resId);
            }
            int skinId = getIdentifier(resId);
            if (skinId == 0) {
                return mAppResources.getString(resId);
            }
            return mSkinResources.getString(skinId);
        } catch (Resources.NotFoundException e) {

        }
        return null;
    }

    public Typeface getTypeface(int resId) {
        String skinTypefacePath = getString(resId);
        if (TextUtils.isEmpty(skinTypefacePath)) {
            return Typeface.DEFAULT;
        }
        try {
            //使用皮肤包
            if (isDefaultSkin) {
                return Typeface.createFromAsset(mAppResources.getAssets(), skinTypefacePath);
            }
            return Typeface.createFromAsset(mSkinResources.getAssets(), skinTypefacePath);
        } catch (RuntimeException e) {
        }
        return Typeface.DEFAULT;
    }
}
