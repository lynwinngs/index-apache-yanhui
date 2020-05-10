package com.index.apache.thinking.in.spring.beans.entity;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName: User
 * @Description: 用户 bean
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 20:45
 * @Version： 1.0
 */
public class User implements BeanNameAware, InitializingBean, DisposableBean {

    private Long id;

    private String name;

    private String beanName;

    @PostConstruct
    public void init() {
        System.out.printf("beanName [%s] @PostConstruct 初始化中...\n",beanName);
    }

    @PreDestroy
    public void done() {
        System.out.printf("beanName [%s] @PreDestroy 销毁中...\n",beanName);
    }

    public void initMethod() {
        System.out.printf("beanName [%s] bean 初始化中...\n",beanName);
    }

    public void destroyMethod() {
        System.out.printf("beanName [%s] bean 销毁中...\n",beanName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("abc");
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void destroy() throws Exception {
        System.out.printf("beanName [%s] DisposableBean 销毁中...\n",beanName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.printf("beanName [%s] InitializingBean 初始化中...\n",beanName);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("bean 回收");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
