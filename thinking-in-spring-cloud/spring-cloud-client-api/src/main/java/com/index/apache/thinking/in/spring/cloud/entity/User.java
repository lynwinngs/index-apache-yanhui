package com.index.apache.thinking.in.spring.cloud.entity;

/**
 * @ClassName User
 * @Description 用户类
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 9:31
 * @Version 1.0
 **/
public class User {

    private Long id;

    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
                                                  