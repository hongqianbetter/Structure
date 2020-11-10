package com.example.daynight;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("ppp",this.toString()+"  onCreate----");
//       SecondActiviy.show(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e("ppp",this.toString()+"  onCreate----2222222");
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e("ppp",this.toString()+"  onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("ppp",this.toString()+"  onRestoreInstanceState");
    }

    public void click(View view){
         SecondActiviy.show(this);
    }
}
