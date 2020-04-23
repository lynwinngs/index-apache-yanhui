package com.index.apache.think.in.spring.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName SingleDependencyLookupDemo
 * @Description 单一依赖查找 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/23 9:50
 * @Version 1.0
 **/
public class SingleDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(SingleDependencyLookupDemo.class);

        applicationContext.refresh();

        dependencyLookup(applicationContext);

        applicationContext.close();
    }

    @Bean
    public String helloWorld() {
        return "hello world";
    }

    private static void dependencyLookup(ApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
    }
}
                                                  