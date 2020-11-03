package com.example.routerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.router_annotation.Route;

import java.net.PasswordAuthentication;


@Route(path = "/main/MainActivity" )
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
