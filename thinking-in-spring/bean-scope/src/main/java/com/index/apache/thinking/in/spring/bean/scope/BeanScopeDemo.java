package com.index.apache.thinking.in.spring.bean.scope;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @ClassName: BeanScopeDemo
 * @Description: Bean 作用域示例
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/10 23:17
 * @Version： 1.0
 */
public class BeanScopeDemo {

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser2;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Bean
    public User singletonUser() {
        return new User(System.nanoTime(), "singleton");
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User prototypeUser() {
        return new User(System.nanoTime(), "prototype");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemo.class);

        applicationContext.refresh();

        getBeanForDifferentScopeByLookup(applicationContext);
        System.out.println("-----------------");
        getBeanForDifferentScopeByInjection(applicationContext);

        applicationContext.close();
    }

    private static void getBeanForDifferentScopeByInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo demo = applicationContext.getBean(BeanScopeDemo.class);

        System.out.println(demo.singletonUser1);
        System.out.println(demo.singletonUser2);
        System.out.println(demo.prototypeUser1);
        System.out.println(demo.prototypeUser2);
    }

    private static void getBeanForDifferentScopeByLookup(AnnotationConfigApplicationContext applicationContext) {

        for (int i = 0; i < 3; i++) {
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println(singletonUser);
        }

        for (int i = 0; i < 3; i++) {
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println(prototypeUser);
        }

    }
}
