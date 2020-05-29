package com.index.apache.thinking.in.spring.bean.lifecycle.entity;

/**
 * @ClassName UserHolder
 * @Description user 包装
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 11:23
 * @Version 1.0
 **/
public class UserHolder {

    private User user;

    public UserHolder() {
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
                                                  