package com.example.objsql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objsql.bean.User;
import com.example.objsql.db.BaseDao;
import com.example.objsql.db.BaseDaoFactory;

public class MainActivity extends AppCompatActivity {

    private TextView content;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);

        ActivityCompat.requestPermissions(this, permissions, 321);

        content = findViewById(R.id.content);
    }


    public void add(View view) {

        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
//        User user = new User();
//        baseDao.insert(user);
        Toast.makeText(MainActivity.this, "222", Toast.LENGTH_SHORT).show();

    }

    public void delete(View view) {

    }

    public void query(View view) {

    }

    public void update(View view) {

    }
}
