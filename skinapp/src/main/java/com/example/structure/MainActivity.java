package com.example.structure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.skinlibrary.SkinManager;

public class MainActivity extends AppCompatActivity {
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);

        ActivityCompat.requestPermissions(this, permissions, 321);
//        SecondActivity.Show(this);

    }

    public void replace(View view) {
        FileUtil.copy(this);
        String path = Environment.getExternalStorageDirectory() + "/skinapk-debug.apk";
        SkinManager.getInstance().loadSkin(path);

    }


    @Override
    protected void onDestroy() {
        SkinManager.getInstance().loadSkin("");
        super.onDestroy();
    }
}