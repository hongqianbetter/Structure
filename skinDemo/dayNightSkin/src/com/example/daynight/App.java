package com.example.daynight;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/29
 */
public class App extends Application {

  ArrayList<Activity> stack= new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifeCircle(stack));
    }


//    public void notifyA(){
//        for (int i=0;i<stack.size();i++){
//            Activity activity = stack.get(i);
//            if(activity instanceof com.example.daynight.SecondActiviy) {
//                ((com.example.daynight.SecondActiviy)activity).update();
//            }
//        }
//    }

    
}




