package com.example.objsql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.objsql.bean.User;
import com.example.objsql.db.BaseDao;
import com.example.objsql.db.BaseDaoFactory;

import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

public class MainActivity extends AppCompatActivity {

    private TextView content;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private EditText ed_id;
    private EditText ed_name;
    private EditText ed_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);

        ActivityCompat.requestPermissions(this, permissions, 321);

        content = findViewById(R.id.content);

        ed_id = findViewById(R.id.ed_id);
        ed_name = findViewById(R.id.ed_name);
        ed_age = findViewById(R.id.ed_age);

    }


    public User getUser() {
        User user = new User();
        int _id = Integer.parseInt(ed_id.getText().toString().trim());
        user.setId(_id);
        String _name = this.ed_name.getText().toString().trim();
        user.setName(_name);
        int _age = Integer.parseInt(this.ed_age.getText().toString().trim());
        user.setAge(_age);
        return user;
    }

    //sqlite3 xx.db 进入数据库
    //Mysql Sqlite不支持boolean


    public void add(View view) {


        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        User user = getUser();
        long insert = baseDao.insert(user);
        Toast.makeText(MainActivity.this, insert + "---", Toast.LENGTH_SHORT).show();

    }

    public void delete(View view) {
        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        User user = new User();
        user.setId(getUser().getId());
        baseDao.delete(user);
    }

    public void query(View view) {
        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);

        User where = new User();
        where.setId(getUser().getId());
        List<User> query = baseDao.query(where);
        content.setText(query.toString());

    }

    public void update(View view) {
        BaseDao<Object> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        User user = new User(getUser().getId(), "PPP", 98);
        User where = new User();
        where.setId(getUser().getId());
        baseDao.update(user, where);
    }


}
