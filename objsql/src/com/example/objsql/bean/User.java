package com.example.objsql.bean;

import com.example.objsql.annotation.DbField;
import com.example.objsql.annotation.DbTable;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/30
 */
@DbTable("tb_user")
public class User {

    @DbField("_id")
    private Integer id;

    @DbField("_name")
    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
