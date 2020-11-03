package com.example.objsql.subdb;

import android.os.Environment;

import com.example.objsql.bean.User;
import com.example.objsql.db.BaseDaoFactory;

import java.io.File;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/2
 */
public enum PrivateDataBaseEnum {
    DATEBASE("");//实例对象

    private String value; //属性

    PrivateDataBaseEnum(String value) {
        this.value=value;
    }

    //用于产生路径
    public String getValue() {
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (userDao != null) {
            User currentUser = userDao.getCurrentUser();
            if (currentUser != null) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

                if (!file.exists()) {
                    file.mkdirs();
                }

                return file.getAbsolutePath() + File.separator + currentUser.getId() + "_login.db";
            }
        }

        return null;
    }
}
