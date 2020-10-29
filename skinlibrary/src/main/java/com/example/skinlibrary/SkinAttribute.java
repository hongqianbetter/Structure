package com.example.skinlibrary;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/20
 */
public class SkinAttribute {
    private static final List<String> mAttributes = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
        mAttributes.add("canvasColor"); //添加自定义View的自定义属性
        mAttributes.add("skinTypeface"); //字体
    }

    List<SkinView> skinViews = new ArrayList<SkinView>();
    Typeface mTypeface;

    public SkinAttribute(Typeface typeface) {
        this.mTypeface = typeface;
    }


    public void load(View view, AttributeSet attrs) {
        List<SkinPair> mSkinPairs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //获取属性名字
            String attributeName = attrs.getAttributeName(i);
            if (mAttributes.contains(attributeName)) {
                //获取属性值
                String attributeValue = attrs.getAttributeValue(i);
                //写死就不替换了 #FF0000
                if (attributeValue.startsWith("#")) {
                    continue;
                }

                int resId;
                //?actionBarSize 表示系统属性值 最终编译会将actionBarSize
                // 替换成R文件中actionBarSize的id值 456789
                if (attributeValue.startsWith("?")) {
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //@drawable  @34567
                    resId = Integer.parseInt(attributeValue.substring(1));
                }

                if (resId != 0) {
                    SkinPair skinPair = new SkinPair(attributeName, resId);
                    mSkinPairs.add(skinPair);
                }
            }
        }
         if(!mSkinPairs.isEmpty()) {
             SkinView skinView = new SkinView(view, mSkinPairs);
             skinView.applySkin(mTypeface);
             skinViews.add(skinView);
         }else if (view instanceof SkinViewSupport || view instanceof TextView) {
            SkinView skinView = new SkinView(view, mSkinPairs);
            skinView.applySkin(mTypeface);
            skinViews.add(skinView);
        }


    }

    public void applySkin() {
        for (SkinView skinView : skinViews) {
            skinView.applySkin(mTypeface);
        }
    }

    public void setTypeface(Typeface skinTypeface) {
        mTypeface = skinTypeface;
    }


    static class SkinView {
        View view;
        List<SkinPair> skinPairs;

        public SkinView(View view, List<SkinPair> skinPairs) {
            this.view = view;
            this.skinPairs = skinPairs;
        }



        public void applySkin(Typeface mTypeface) {
            applySkinSupport(view); //支持自定义View
            applyTypeface(mTypeface); //支持TextView字体  TextView默认支持字体 不需要额外指定SkinTypeface

            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, top = null, right = null, bottom = null;
                switch (skinPair.attributeName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(skinPair
                                .resId);
                        if (background instanceof Integer) {
                            view.setBackgroundColor((int) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;
                    case "src":
                        background = SkinResources.getInstance().getBackground(skinPair
                                .resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer)
                                    background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        ((TextView) view).setTextColor(SkinResources.getInstance().getColorStateList
                                (skinPair.resId));
                        break;
                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "skinTypeface":
                        applyTypeface(SkinResources.getInstance().getTypeface
                                (skinPair.resId));
                        break;
                    default:
                        break;
                }
                if (null != left || null != right || null != top || null != bottom) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right,
                            bottom);
                }
            }
        }

        private void applyTypeface(Typeface typeface) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        }

        private void applySkinSupport(View v) {
            if (v instanceof SkinViewSupport) {
                ((SkinViewSupport) v).applySkin();
            }
        }
    }

    static class SkinPair {
        String attributeName;
        int resId;

        public SkinPair(String attributeName, int resId) {
            this.attributeName = attributeName;
            this.resId = resId;
        }
    }
}
