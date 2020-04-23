package com.index.apache.think.in.spring.beans;

import com.index.apache.think.in.spring.beans.entity.DefaultUserFactory;
import com.index.apache.think.in.spring.beans.entity.UserFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @ClassName: ServiceLoaderBeanInstantiationDemo
 * @Description: {@link java.util.ServiceLoader} bean 实例化 demo
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/19 23:07
 * @Version： 1.0
 */
public class ServiceLoaderBeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("META-INF/bean-instantiation-context.xml");

        ServiceLoader<UserFactory> bean = beanFactory.getBean("user-by-service-loader", ServiceLoader.class);

        // ServiceLoaderFactoryBean 方式
        serviceLoaderDemo(bean);

        // ServiceLoader 方式
        serviceLoaderDemo(ServiceLoader.load(UserFactory.class));
    }

    private static void serviceLoaderDemo(ServiceLoader<UserFactory> serviceLoader) {
        for (UserFactory userFactory : serviceLoader) {
            System.out.println(userFactory.getUser());
        }
    }
}
