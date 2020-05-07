package com.index.apache.thinking.in.spring.dependency.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * @ClassName: DependencySourceDemo
 * @Description: 依赖来源示例
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/7 22:19
 * @Version： 1.0
 */
public class DependencySourceDemo {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void dependencyInjection() {
        System.out.println("依赖注入 BeanFactory 实例:" + beanFactory);
        System.out.println("依赖注入 ApplicationContext 实例:" + applicationContext);
        System.out.println("依赖注入 ResourceLoader 实例:" + resourceLoader);
        System.out.println("依赖注入 ApplicationEventPublisher 实例:" + applicationEventPublisher);
    }

    @PostConstruct
    public void dependencyLookup() {
        getBean(beanFactory, BeanFactory.class);
        getBean(beanFactory, ApplicationContext.class);
        getBean(beanFactory, ResourceLoader.class);
        getBean(beanFactory, ApplicationEventPublisher.class);
    }

    private <T> void getBean(BeanFactory beanFactory, Class<T> beanClass) {
        try {
            System.out.println(beanFactory.getBean(beanClass));
        } catch (Exception e) {
            System.err.printf("依赖查找 %s 实例\n", beanClass.getName());
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(DependencySourceDemo.class);

        applicationContext.refresh();

        applicationContext.close();
    }
}
