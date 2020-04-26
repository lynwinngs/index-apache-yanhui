package com.index.apache.thinking.in.spring.beans.entity;

import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName: UserFactoryBean
 * @Description: 用户 FactoryBean
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 23:03
 * @Version： 1.0
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
