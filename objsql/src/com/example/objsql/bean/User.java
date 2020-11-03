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
    private String id;

    @DbField("_name")
    private String name;

    private Integer age;

    private Integer status;


    public User() {
    }

    public User(String id, String name, Integer age, Integer status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
