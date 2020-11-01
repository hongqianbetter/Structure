package com.example.contextdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

public class ThirdActivity extends AppCompatActivity {


    public static void show(Context mContext) {
        Intent intent = new Intent(mContext, ThirdActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }


    public void send(View view) {
        ((App)getApplication()).notifyA();
    }



}