package com.example.daynight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ThirdActivity extends AppCompatActivity {


    public static void show(Context mContext) {
        Intent intent = new Intent(mContext, ThirdActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Log.e("ppp",this.toString()+"  onCreate----");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ppp",this.toString()+"  onResume----");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ppp",this.toString()+"  onDestory----");
    }

    public void send(View view) {
        night(view);
    }


    /**
     * 夜间模式
     * @param view
     */
    public void night(View view) {
        //获取当前的模式，设置相反的模式，这里只使用了，夜间和非夜间模式
        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode != Configuration.UI_MODE_NIGHT_YES) {
            //保存夜间模式状态,Application中可以根据这个值判断是否设置夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            //ThemeConfig主题配置，这里只是保存了是否是夜间模式的boolean值
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        recreate();//需要recreate才能生效
    }




}