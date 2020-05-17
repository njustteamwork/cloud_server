package com.njust.cloud_server.dao;

import com.njust.cloud_server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDAO
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(User user){
        return jdbcTemplate.update("insert into user(user_name, password) values(?, ?)",user.getUserName(),user.getPassword());
    }

    public boolean verify(User user){
        List<User> list =  jdbcTemplate.query("select * from user where user_name = ?", new BeanPropertyRowMapper(User.class), user.getUserName());
        if(list != null && list.size()>0){
            User oUser = list.get(0);
            if(oUser.getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }
}
