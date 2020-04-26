package com.index.apache.thinking.in.spring.beans.entity;

/**
 * @ClassName: UserFactory
 * @Description: 用户工厂
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 22:54
 * @Version： 1.0
 */
public interface UserFactory {

    default User getUser(){
        return User.createUser();
    }
}
