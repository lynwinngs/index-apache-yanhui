package com.index.apache.think.in.spring.beans;

import com.index.apache.think.in.spring.beans.entity.DefaultUserFactory;
import com.index.apache.think.in.spring.beans.entity.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: AutowireCapableBeanInstantiationDemo
 * @Description: {@link AutowireCapableBeanFactory} 方式 bean 实例化
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 23:21
 * @Version： 1.0
 */
public class AutowireCapableBeanInstantiationDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/bean-instantiation-context.xml");

        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();

        // 创建的类不能是接口或抽象类
//        UserFactory bean = autowireCapableBeanFactory.createBean(UserFactory.class);
        UserFactory bean = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);

        System.out.println(bean.getUser());
    }
}
