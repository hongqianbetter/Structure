package com.example.objsql.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.objsql.L;
import com.example.objsql.annotation.DbField;
import com.example.objsql.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
                if (columName.equals(fieldName)) { //匹配字段 将数据库某一个字段名 与 属性相匹配
                    columnField = field;
                    break;
                }
            }

            if (columnField != null) { //匹配到
                cacheMap.put(columName, columnField);  //将Key数据库字段名作为key 属性名value
            }
        }


    }

    private String getCreateSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("create table if not exists ");
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
        HashMap hashMap = getFieldValues(entity);
        ContentValues contentValues = getContentValues(hashMap);

        long insert = database.insert(tableName, null, contentValues);
        return insert;
    }

    private ContentValues getContentValues(HashMap<String, String> map) {
        ContentValues values = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) { //key 数据库字段名  value 该属性值
            String key = iterator.next();
            String value = map.get(key);
            if (null != value) {
                values.put(key, value);
            }
        }

        return values;

    }

    //将对象的属性值 添加到HashMap中
    private HashMap getFieldValues(T entity) {
        //key 数据库字段名  value 数据库字段值
        HashMap<String, String> map = new HashMap<>();
        //从entity中获取属性值 只不过不知道该class属性的类型
        //        Class aClass = entity.getClass();
        //        Field[] declaredFields = aClass.getDeclaredFields(); //反射比较耗费性能 这里木有必要用反射了  用缓存就可以了
        Iterator<Field> iterator = cacheMap.values().iterator(); //从map中获取属性名 如果对象的该属性值不为null 就将他放入HashMap中
        while (iterator.hasNext()) {
            Field next = iterator.next();
            next.setAccessible(true);
            try {
                //获取对象的属性值
                Object o = next.get(entity);
                if (o == null) {
                    continue;
                }
                String value = o.toString();
                String key;
                if (null != next.getAnnotation(DbField.class)) {
                    key = next.getAnnotation(DbField.class).value();
                } else {
                    key = next.getName();
                }

                if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
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
        //        database.update(tableName,contentValues,"name = ? ",new String[]{"ddfff"});
        //准备好ContentValues中的数据
        HashMap values = getFieldValues(entity);
        ContentValues contentValues = getContentValues(values);

        //条件Map
        HashMap values1 = getFieldValues(where);
        Condition condition = new Condition(values1);
        int result = database.update(tableName, contentValues, condition.whereClause, condition.whereArgs);

        return result;
    }

    private class Condition {
        private String whereClause;
        private String[] whereArgs;

        public Condition(Map<String, String> where) {
            ArrayList list = new ArrayList();

            StringBuffer buffer = new StringBuffer();
            buffer.append("1=1"); //todo 相当于木有 这里只是占一个位置 符合语法规范

            //取得所有成员变量的名字
            Set<String> keySet = where.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String values = where.get(key);
                if (values != null) {
                    buffer.append(" and " + key + "=?");
                    list.add(values);
                }
            }

            this.whereClause = buffer.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);
        }
    }

    @Override
    public int delete(T where) {
        HashMap values = getFieldValues(where);
        Condition condition = new Condition(values);
        int delete = database.delete(tableName, condition.whereClause, condition.whereArgs);

        return delete;
    }

    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer start, Integer end) {
        //        query(String table, String[] columns, String selection,
        //                String[] selectionArgs, String groupBy, String having,
        //                String orderBy, String limit) {
        HashMap map = getFieldValues(where);
        String limitStr = "";
        if (null != start && null != end) {
            limitStr = start + " , " + end;
        }
        Condition condition = new Condition(map);
        Cursor query = database.query(tableName, null, condition.whereClause,
                condition.whereArgs, null, orderBy, limitStr);


        return getQueryResult(query, where);

    }



    private List<T> getQueryResult(Cursor query, T where) {
        List list = new ArrayList<>();

        Object item = null; //给对象赋值

        while (query.moveToNext()) {
            try {
                item = where.getClass().newInstance();
                // 数据库字段名
                Iterator<Map.Entry<String, Field>> iterator = cacheMap.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, Field> entry = iterator.next();

                    //相当于数据库中的列名
                    String columnName = entry.getKey(); //key 可能是 注解值 也可能是Field的名字
                    //或取在数据库中的下标
                    int columnIndex = query.getColumnIndex(columnName);
                    if (columnIndex != -1) {
                        Field value = entry.getValue();
                        Class type = value.getType();
                        if (type == String.class) {
                            value.set(item, query.getString(columnIndex));

                        } else if (type == Integer.class) {

                            value.set(item, query.getInt(columnIndex));

                        } else if (type == Long.class) {

                            value.set(item, query.getLong(columnIndex));

                        } else if (type == byte[].class) {
                            value.set(item, query.getBlob(columnIndex));
                        } else {

                            continue;
                        }
                    }

                }
                list.add(item);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
        query.close();
        return list; //会被强转
    }
}
