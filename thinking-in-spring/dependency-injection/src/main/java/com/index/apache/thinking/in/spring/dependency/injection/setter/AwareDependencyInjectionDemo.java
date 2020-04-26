package com.index.apache.thinking.in.spring.dependency.injection.setter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName AwareDependencyInjectionDemo
 * @Description 基于 Aware 方式的依赖注入 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 11:48
 * @Version 1.0
 **/
public class AwareDependencyInjectionDemo implements BeanFactoryAware, ApplicationContextAware {

    private static BeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AwareDependencyInjectionDemo.class);

        applicationContext.refresh();

        System.out.println(AwareDependencyInjectionDemo.beanFactory == applicationContext.getBeanFactory());
        System.out.println(AwareDependencyInjectionDemo.applicationContext == applicationContext);

        applicationContext.close();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareDependencyInjectionDemo.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareDependencyInjectionDemo.applicationContext = applicationContext;
    }
}
                                                  