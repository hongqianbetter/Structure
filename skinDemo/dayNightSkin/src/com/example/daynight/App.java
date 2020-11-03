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
//        Log.e("DDD",getBaseContext().toString()+"  app baseContext");
//        Log.e("DDD",this.toString()+"  app ");

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                 stack.add(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                      stack.remove(activity);
            }
        });
    }


    public void notifyA(){
        for (int i=0;i<stack.size();i++){
            Activity activity = stack.get(i);
            if(activity instanceof com.example.daynight.SecondActiviy) {
                ((com.example.daynight.SecondActiviy)activity).update();
            }
        }
    }

    
}




