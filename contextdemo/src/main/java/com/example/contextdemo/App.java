package com.example.contextdemo;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/29
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Log.e("DDD",getBaseContext().toString()+"  app baseContext");
//        Log.e("DDD",this.toString()+"  app ");
    }
}




