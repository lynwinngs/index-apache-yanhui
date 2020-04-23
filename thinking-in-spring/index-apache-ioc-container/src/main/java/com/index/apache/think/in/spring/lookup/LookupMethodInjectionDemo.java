package com.index.apache.think.in.spring.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: LookupMethodInjectionDemo
 * @Description: 通过  Lookup Method Injection 方式解决单例 bean 依赖 原型 bean ，生命周期不一致的情况
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/23 21:53
 * @Version： 1.0
 * <p>
 * https://docs.spring.io/spring/docs/5.2.5.RELEASE/spring-framework-reference/core.html#beans-factory-method-injection
 * @see UserManager
 */
public class LookupMethodInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(UserManager.class);

        applicationContext.refresh();

        UserManager bean = applicationContext.getBean(UserManager.class);

        User user1 = bean.manage();
        User user2 = bean.manage();
        User user3 = bean.manage();

        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
    }
}
