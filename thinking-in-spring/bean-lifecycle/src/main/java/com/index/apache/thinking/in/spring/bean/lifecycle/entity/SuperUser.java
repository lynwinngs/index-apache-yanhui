package com.index.apache.thinking.in.spring.bean.lifecycle.entity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName: User
 * @Description: 用户 bean
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 20:45
 * @Version： 1.0
 */
public class SuperUser extends User {

    private String city;

    @PostConstruct
    @Override
    public void init() {
        System.out.printf("beanName [%s] @PostConstruct 初始化中...\n", beanName);
    }

    @PreDestroy
    @Override
    public void done() {
        System.out.printf("beanName [%s] @PreDestroy 销毁中...\n", beanName);
    }

    public SuperUser() {
    }

    public SuperUser(Long id, String name, String city) {
        super(id, name);
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "city='" + city + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
