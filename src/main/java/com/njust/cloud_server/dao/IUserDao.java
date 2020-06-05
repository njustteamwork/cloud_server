package com.njust.cloud_server.dao;

import com.njust.cloud_server.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao {

    void add(User user);

    User findByName(String userName);

}
