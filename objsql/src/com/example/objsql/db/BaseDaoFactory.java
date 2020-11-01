package com.example.objsql.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/30
 */
public class BaseDaoFactory {

    private static final BaseDaoFactory sInstance = new BaseDaoFactory();

    private SQLiteDatabase database;

    //定义建数据库的路径
    private String dataBasePath;


    public static BaseDaoFactory getInstance() {
        return sInstance;
    }


    private BaseDaoFactory() {
        //新建数据库

        dataBasePath = Environment.getExternalStorageDirectory() + File.separator+"aql.db";
        database = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);

    }

    public <T> BaseDao<T> getBaseDao(Class entityClass){
        BaseDao baseDao=null;

        try {
            baseDao=BaseDao.class.newInstance();
            baseDao.init(database,entityClass);
        }catch (Exception e){

        }
        return baseDao;
    }

}
