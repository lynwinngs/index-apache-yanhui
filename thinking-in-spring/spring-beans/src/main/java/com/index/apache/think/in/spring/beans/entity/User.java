package com.index.apache.think.in.spring.beans.entity;

/**
 * @ClassName: User
 * @Description: 用户 bean
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 20:45
 * @Version： 1.0
 */
public class User {

    private Long id;

    private String name;

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
}
