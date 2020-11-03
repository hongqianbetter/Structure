package com.example.objsql.subdb;

import androidx.core.app.NavUtils;

import com.example.objsql.L;
import com.example.objsql.bean.User;
import com.example.objsql.db.BaseDao;

import java.util.List;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/11/2
 */
public class UserDao extends BaseDao<User> {


    @Override
    public long insert(User entity) {
        //查询tb_user
        List<User> list = query(new User());
        User where = null;

        for (User user : list) { //对所有用户进行状态改变
            where = new User();
            where.setId(user.getId());
            user.setStatus(0);
            update(user, where);
        }

        entity.setStatus(1);
        return super.insert(entity);
    }

    //得到当前登录的User
    public User getCurrentUser() {
        User user = new User();
        user.setStatus(1);
        List<User> list = query(user);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        return null;
    }
}
