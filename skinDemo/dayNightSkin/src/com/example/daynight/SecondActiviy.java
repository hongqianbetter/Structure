package com.example.daynight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActiviy extends AppCompatActivity {

    public static void show(Context mContext) {
        Intent intent = new Intent(mContext, SecondActiviy.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_avtiviy);

        Log.e("ppp",this.toString()+"  onCreate----");

    }


    public void update(){

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


    public void click(View view){
        ThirdActivity.show(this);
    }
}