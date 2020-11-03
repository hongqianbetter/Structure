package com.example.objsql.subdb;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/2
 */
public class UserImg {
    private String time;

    private String imgPath;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "UserImg{" +
                "time='" + time + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
