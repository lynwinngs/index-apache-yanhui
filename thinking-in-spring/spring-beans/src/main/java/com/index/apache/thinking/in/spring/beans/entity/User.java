package com.index.apache.thinking.in.spring.beans.entity;

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
public class User implements InitializingBean, DisposableBean {

    private Long id;

    private String name;

    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct 初始化中...");
    }

    @PreDestroy
    public void done(){
        System.out.println("@PreDestroy 销毁中...");
    }

    public void initMethod(){
        System.out.println("bean 初始化中...");
    }

    public void destroyMethod(){
        System.out.println("bean 销毁中...");
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
        System.out.println("DisposableBean 销毁中...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean 初始化中...");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("bean 回收");
    }
}
