package com.index.apache.thinking.in.spring.beans;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName SingletonBeanRegistryDemo
 * @Description 单一 bean 注册示例
 * @Author xiaoxuezhi
 * @DATE 2020/5/6 13:37
 * @Version 1.0
 **/
public class SingletonBeanRegistryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();
        beanFactory.registerSingleton("user",new User(1L,"xxz"));

        applicationContext.refresh();

        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
    }
}
                                                  