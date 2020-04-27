package com.index.apache.thinking.in.spring.dependency.injection.setter;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @ClassName QualifierAnnotationDependencyInjectionDemo
 * @Description 限定的依赖注入
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 18:18
 * @Version 1.0
 **/
public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private List<User> allUser;

    @Autowired
    @Qualifier
    private List<User> qualifierUser;

    @Autowired
    @UserAnnotation
    private List<User> specificUser;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        applicationContext.refresh();

        QualifierAnnotationDependencyInjectionDemo bean = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        System.out.println(bean.allUser);
        System.out.println(bean.qualifierUser);
        System.out.println(bean.specificUser);

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

    @Bean
    @Qualifier
    public User user3(){
        User user = new User();
        user.setId(5L);
        return user;
    }

    @Bean
    @Qualifier
    public User user4(){
        User user = new User();
        user.setId(6L);
        return user;
    }

    @Bean
    @UserAnnotation
    public User user5(){
        User user = new User();
        user.setId(7L);
        return user;
    }

    @Bean
    @UserAnnotation
    public User user6(){
        User user = new User();
        user.setId(8L);
        return user;
    }
}
                                                  