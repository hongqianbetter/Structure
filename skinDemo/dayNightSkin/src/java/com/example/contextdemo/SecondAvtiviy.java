package com.example.contextdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondAvtiviy extends AppCompatActivity {

    public static void show(Context mContext) {
        Intent intent = new Intent(mContext, SecondAvtiviy.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_avtiviy);

        Log.e("ppp","onCreate----");
        ThirdActivity.show(this);
    }


    public void update(){
        recreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ppp","onResume----");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ppp","onDestory----");
    }
}