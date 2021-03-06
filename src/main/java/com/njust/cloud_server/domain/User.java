package com.njust.cloud_server.domain;

/**
 * 用户实体类
 */
public class User {
    Long id;
    String userName;
    String password;

    public User() {};

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
