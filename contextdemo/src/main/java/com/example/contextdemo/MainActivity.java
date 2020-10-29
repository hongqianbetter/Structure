package com.example.contextdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int [] attrsId= new int[]{R.attr.lll,R.attr.rrr};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypedArray typedArray = obtainStyledAttributes(attrsId);
        for (int i=0;i<typedArray.length();i++){
            int llId = typedArray.getResourceId(0,0);
            Toast.makeText(MainActivity.this, getResources().getString(llId), Toast.LENGTH_SHORT).show();
        }


    }
}
