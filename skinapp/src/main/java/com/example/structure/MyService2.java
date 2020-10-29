package com.example.structure;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;

public class MyService2 extends Service {
    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.e("777",getResources().toString()+"Service2");
//        Log.e("777",getAssets().toString()+"Service2");

        return super.onStartCommand(intent, flags, startId);
    }
}
