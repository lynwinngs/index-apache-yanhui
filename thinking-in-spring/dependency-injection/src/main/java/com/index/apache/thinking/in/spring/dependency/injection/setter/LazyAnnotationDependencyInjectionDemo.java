package com.index.apache.thinking.in.spring.dependency.injection.setter;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName LazyAnnotationDependencyInjectionDemo
 * @Description 延迟依赖注入
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 18:18
 * @Version 1.0
 **/
public class LazyAnnotationDependencyInjectionDemo {

    @Autowired
    private ObjectProvider<User> userObjectProvider;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        
        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

        applicationContext.refresh();

        LazyAnnotationDependencyInjectionDemo bean = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);
        bean.userObjectProvider.forEach(System.out::println);

        applicationContext.close();
    }

    @Bean
    public User user1(){
        User user = new User();
        user.setId(3L);
        return user;
    }

    @Bean
    public User user2(){
        User user = new User();
        user.setId(4L);
        return user;
    }
}
                                                  