package com.example.objsql.db;

import java.util.List;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/30
 */
public interface IBaseDao<T> {

    //插入
    long insert(T entity);

    //更新
    long update(T entity,T where);

    //删除
    int delete(T where);

    //查询
    List<T> query(T where);

    //查询
    List<T> query(T where,String orderBy,Integer start,Integer end);
}
