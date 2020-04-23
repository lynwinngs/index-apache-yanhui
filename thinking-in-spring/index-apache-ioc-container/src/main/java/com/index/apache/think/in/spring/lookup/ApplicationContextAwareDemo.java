package com.index.apache.think.in.spring.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Random;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @ClassName ApplicationContextAwareDemo
 * @Description {@link ApplicationContextAware} 解决单例 bean 依赖 原型 bean ，生命周期不一致的情况
 * @Author xiaoxuezhi
 * @DATE 2020/4/23 10:21
 * @Version 1.0
 * <p>
 * https://docs.spring.io/spring/docs/5.2.5.RELEASE/spring-framework-reference/core.html#beans-factory-method-injection
 * <p>
 * 参考官方文档，这种方式对 Spring 框架有强依赖性，不建议使用
 * 推荐使用  Lookup Method Injection 方式
 * @see LookupMethodInjectionDemo
 * @see UserManager
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
                                                  