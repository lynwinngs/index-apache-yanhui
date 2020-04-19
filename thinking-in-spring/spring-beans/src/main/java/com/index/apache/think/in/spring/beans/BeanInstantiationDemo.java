package com.index.apache.think.in.spring.beans;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: BeanInstantiationDemo
 * @Description: Bean 实例化 demo
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 22:52
 * @Version： 1.0
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/bean-instantiation-context.xml");

        User user = beanFactory.getBean("user-by-static-method", User.class);

        System.out.println("静态方法实例化：" + user);

        User user2 = beanFactory.getBean("user-by-instance-method", User.class);

        System.out.println("实例方法实例化：" + user2);

        User user3 = beanFactory.getBean("user-by-factory-bean", User.class);

        System.out.println("工厂方法实例化：" + user3);

        System.out.println(user == user2);
        System.out.println(user2 == user3);
        System.out.println(user == user3);
    }
}
