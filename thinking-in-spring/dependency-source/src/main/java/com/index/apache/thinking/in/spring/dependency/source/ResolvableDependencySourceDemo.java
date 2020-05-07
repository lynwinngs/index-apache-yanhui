package com.index.apache.thinking.in.spring.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @ClassName: ResolvableDependencySourceDemo
 * @Description: Resolvable Dependency 来源示例
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/7 22:46
 * @Version： 1.0
 */
public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;

    @PostConstruct
    public void init(){
        System.out.println(value);
    }

    public static void main(String[] args) {
        method1();
    }

    /**
     * 错误，AnnotationConfigApplicationContext#getAutowireCapableBeanFactory 内有 beanFactory active 校验
     * 在 refresh 前调用该方法会报错
     */
    private static void method1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if(autowireCapableBeanFactory instanceof ConfigurableListableBeanFactory){
            ConfigurableListableBeanFactory beanFactory = ConfigurableListableBeanFactory.class.cast(autowireCapableBeanFactory);
            beanFactory.registerResolvableDependency(String.class,"Hello World");
        }

        applicationContext.register(DependencySourceDemo.class);

        applicationContext.refresh();

        applicationContext.close();
    }

    /**
     * 错误，向 applicationContext 注册了 ResolvableDependencyInjectionDemo 且已经 refresh，无法再注册
     */
    private static void method2(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ResolvableDependencySourceDemo.class);

        applicationContext.refresh();

        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if(autowireCapableBeanFactory instanceof ConfigurableListableBeanFactory){
            ConfigurableListableBeanFactory beanFactory = ConfigurableListableBeanFactory.class.cast(autowireCapableBeanFactory);
            beanFactory.registerResolvableDependency(String.class,"Hello World");
        }

        applicationContext.close();
    }

    /**
     * 正确，在 refresh 后，再进行注册，此时并不会对注册 bean 进行实例化
     */
    private static void method3(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.refresh();

        applicationContext.register(ResolvableDependencySourceDemo.class);

        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if(autowireCapableBeanFactory instanceof ConfigurableListableBeanFactory){
            ConfigurableListableBeanFactory beanFactory = ConfigurableListableBeanFactory.class.cast(autowireCapableBeanFactory);
            beanFactory.registerResolvableDependency(String.class,"Hello World");
        }

        ResolvableDependencySourceDemo demo = applicationContext.getBean(ResolvableDependencySourceDemo.class);
//        System.out.println(demo.value);

        applicationContext.close();
    }

    /**
     * 正确，使用 beanFactoryPostProcessor，在获取 beanFactory 后处理时进行注册
     */
    private static void method4(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ResolvableDependencySourceDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class,"Hello World");
        });

        applicationContext.refresh();

        applicationContext.close();
    }

    /**
     * 正确，AnnotationConfigApplicationContext#getBeanFactory 内没有 beanFactory active 校验
     * 在 refresh 前调用不会报错
     * 区别于 method1
     */
    private static void method5(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        ConfigurableListableBeanFactory autowireCapableBeanFactory = applicationContext.getBeanFactory();
        autowireCapableBeanFactory.registerResolvableDependency(String.class,"Hello World");

        applicationContext.register(ResolvableDependencySourceDemo.class);

        applicationContext.refresh();

        applicationContext.close();
    }
}
