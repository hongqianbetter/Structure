package com.example.objsql.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.objsql.L;
import com.example.objsql.annotation.DbField;
import com.example.objsql.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/30
 */
public class BaseDao<T> implements IBaseDao<T> {

    //持有数据库操作的引用
    private SQLiteDatabase database;

    //表名
    private String tableName;

    //持有数据库所对应的Java类型

    private Class<T> entityClass;

    //标记用来表示是否做过初始化操作
    private boolean isInit;

    protected void init(SQLiteDatabase database, Class<T> entityClass) {
        this.database = database;
        this.entityClass = entityClass;

        //可以根据传入的对象类型来建表 只需要建一次
        if (!isInit) {
            //自动建表
            if (null != entityClass.getAnnotation(DbTable.class)) {
                this.tableName = entityClass.getAnnotation(DbTable.class).value();
            } else {
                this.tableName = entityClass.getSimpleName();
            }
            //create table if not exists tb_user();
            String createTableSql = getCreateSql();
            this.database.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
    }

    private void initCacheMap() {
        //取得所有字段名
        String sql = "select * from " + tableName + " limit 1,0";//空表
        Cursor cursor = database.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        //取所有的成员变量
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true);

        }
        //字段 跟 对象成员变量 一一 对应
        for (String columName : columnNames) {
            Field columnField = null;
            for (Field field : declaredFields) {
                String fieldName = ""; //对象中的成员变量名字
                if (null != field.getAnnotation(DbField.class)) {
                    fieldName = field.getAnnotation(DbField.class).value();
                } else {
                    fieldName = field.getName();
                }
                if (columName.equals(fieldName)) { //匹配字段
                    columnField = field;
                    break;
                }
            }

            if (columnField != null) { //匹配到
                cacheMap.put(columName, columnField);
            }
        }


    }

    private String getCreateSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("create table if not exits ");
        buffer.append(tableName + "(");

        //反射得到所有的成员变量
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class<?> type = field.getType();//拿到成员类型
            if (null != field.getAnnotation(DbField.class)) {
                if (type == String.class) {
                    buffer.append(field.getAnnotation(DbField.class).value() + " TEXT,");
                } else if (type == Integer.class) {
                    buffer.append(field.getAnnotation(DbField.class).value() + " INTEGER,");
                } else if (type == Long.class) {
                    buffer.append(field.getAnnotation(DbField.class).value() + " BIGINT,");
                } else if (type == Double.class) {
                    buffer.append(field.getAnnotation(DbField.class).value() + "DOUBLE,");
                } else if (type == byte[].class) {
                    buffer.append(field.getAnnotation(DbField.class).value() + "BLOB,");
                } else if (type == Boolean.class) {
                    buffer.append(field.getAnnotation(DbField.class).value() + "BOOLEAN,");
                } else {
                    continue;
                }
            } else {
                if (type == String.class) {
                    buffer.append(field.getName() + " TEXT,");
                } else if (type == Integer.class) {
                    buffer.append(field.getName() + " INTEGER,");
                } else if (type == Long.class) {
                    buffer.append(field.getName() + " BIGINT,");
                } else if (type == Double.class) {
                    buffer.append(field.getName() + "DOUBLE,");
                } else if (type == byte[].class) {
                    buffer.append(field.getName() + "BLOB,");
                } else if (type == Boolean.class) {
                    buffer.append(field.getName() + "BOOLEAN,");
                } else {
                    //不支持的类型
                    continue;
                }
            }
        }

        if (buffer.charAt(buffer.length() - 1) == ',') {
            buffer.deleteCharAt(buffer.length() - 1);
        }

        buffer.append(")");
        L.e(buffer.toString());
        return buffer.toString();
    }

    //定义一个缓存空间
    HashMap<String, Field> cacheMap = new HashMap<>();

    @Override
    public long insert(T entity) {
        HashMap hashMap = getValues(entity);
        ContentValues contentValues = getContentValues(hashMap);

        long insert = database.insert(tableName, null, contentValues);
        return insert;
    }

    private ContentValues getContentValues(HashMap<String, String> map) {
        ContentValues values = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(keys);
            if (null != value) {
                values.put(key, value);
            }
        }

        return values;

    }

    private HashMap getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field next = iterator.next();
            next.setAccessible(true);
            try {
                //获取对象的属性
                Object o = next.get(entity);
                if (o == null) {
                    continue;
                }
                String value = o.toString();
                String key ;
                if (null != next.getAnnotation(DbField.class)) {
                    key = next.getAnnotation(DbField.class).value();
                } else {
                    key = next.getName();
                }

                if (!TextUtils.isEmpty(key)) {
                    map.put(key, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public long update(T entity, T where) {
        return 0;
    }

    @Override
    public int delete(T where) {
        return 0;
    }

    @Override
    public List query(T where) {
        return null;
    }

    @Override
    public List query(T where, String orderBy, Integer start, Integer end) {
        return null;
    }
}
