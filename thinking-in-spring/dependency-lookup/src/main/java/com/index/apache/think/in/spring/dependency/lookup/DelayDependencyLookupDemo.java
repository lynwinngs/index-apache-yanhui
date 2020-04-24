package com.index.apache.think.in.spring.dependency.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName DelayDependencyLookupDemo
 * @Description 延迟依赖查找 demo
 * 这里的延迟和 lambda 的延迟是相同的概念，不是真正意义上的延迟，
 * 这里延迟的概念即，在将行为作为参数传入方法时，并不会执行行为，而是当真正调用函数式接口的方法时，才真正执行行为，所以称之为延迟
 * @Author xiaoxuezhi
 * @DATE 2020/4/24 11:04
 * @Version 1.0
 **/
public class DelayDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DelayDependencyLookupDemo.class);

        applicationContext.refresh();

        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
//        System.out.println(beanProvider.getObject()); // 非安全，会报错
        System.out.println(beanProvider.getIfAvailable());
        System.out.println(beanProvider.getIfAvailable(User::new));

        applicationContext.close();
    }


}
                                                  