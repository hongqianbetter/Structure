package com.example.objsql.db;

import java.util.List;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/2
 */
public class BaseDaoImpl<T> extends BaseDao<T> {


    //去复写扩展
    @Override
    public List<T> query(T where) {
        return super.query(where);
    }
}
