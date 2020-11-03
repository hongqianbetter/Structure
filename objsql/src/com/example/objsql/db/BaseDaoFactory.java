package com.example.objsql.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/30
 */
public class BaseDaoFactory {

    private static final BaseDaoFactory sInstance = new BaseDaoFactory();

    private SQLiteDatabase database;

    //定义建数据库的路径
    private String dataBasePath;

    protected Map<String,BaseDao> map= Collections.synchronizedMap(new HashMap<String, BaseDao>());


    public static BaseDaoFactory getInstance() {
        return sInstance;
    }


    public BaseDaoFactory() {
        //新建数据库

        dataBasePath = Environment.getExternalStorageDirectory() + File.separator + "user.db";
        database = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);

    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao baseDao = null;

        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(database, entityClass);
        } catch (Exception e) {
            Log.e("SSS", e.getMessage());
        }
        return baseDao;
    }



    public <T extends BaseDao<M>, M> T getBaseDao(
            Class<T> daoClass, Class<M> entityClass) {

        BaseDao baseDao = null;
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(database, entityClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

}
