package com.example.objsql.subdb;

import android.database.sqlite.SQLiteDatabase;

import com.example.objsql.db.BaseDao;
import com.example.objsql.db.BaseDaoFactory;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/2
 */
public class BaseDaoSubFactory extends BaseDaoFactory {

    //定义一个用于实现分库的数据库操作对象
    private static SQLiteDatabase sqLiteDatabase;

    private static final BaseDaoSubFactory instance = new BaseDaoSubFactory();

    public static BaseDaoSubFactory getInstance() {
        return instance;
    }


    public <T extends BaseDao<M>, M> T getBaseDao(
            Class<T> daoClass, Class<M> entityClass) {

        BaseDao baseDao = null;

        if (map.get(PrivateDataBaseEnum.DATEBASE.getValue()) != null) {
            return (T) map.get(PrivateDataBaseEnum.DATEBASE.getValue());
        }

        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(PrivateDataBaseEnum.DATEBASE.getValue(), null);
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            map.put(PrivateDataBaseEnum.DATEBASE.getValue(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

}
