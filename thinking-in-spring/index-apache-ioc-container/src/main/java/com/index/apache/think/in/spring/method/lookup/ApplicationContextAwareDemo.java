package com.index.apache.think.in.spring.method.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Random;
import java.util.RandomAccess;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @ClassName ApplicationContextAwareDemo
 * @Description {@link ApplicationContextAware} 解决单例
 * @Author xiaoxuezhi
 * @DATE 2020/4/23 10:21
 * @Version 1.0
 **/
public class ApplicationContextAwareDemo implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationContextAwareDemo.class);
        ApplicationContextAwareDemo demo = new ApplicationContextAwareDemo();
        demo.setApplicationContext(applicationContext);
        applicationContext.refresh();
        for (int i = 0; i < 3; i++) {
            System.out.println(demo.createUser());
        }
    }

    public User createUser() {
        return this.applicationContext.getBean("user", User.class);
    }

    @Bean
    @Scope(value = SCOPE_PROTOTYPE)
    public User user() {
        User user = new User();
        user.setId((long) new Random().nextInt(100));
        user.setName("aaa");
        return user;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
                                                  