package com.index.apache.think.in.spring.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Random;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @ClassName: UserManager
 * @Description: 通过  Lookup Method Injection 方式解决单例 bean 依赖 原型 bean ，生命周期不一致的情况
 * 原理：对标注 @Lookup 注解 或在 xml 使用 lookup-method 属性的类进行 CGLib 动态代理，生成子类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/23 21:55
 * @Version： 1.0
 * <p>
 * https://docs.spring.io/spring/docs/5.2.5.RELEASE/spring-framework-reference/core.html#beans-factory-method-injection
 * @see LookupMethodInjectionDemo
 */
public abstract class UserManager {

    public User manage() {
        User user = createUser();
        return user;
    }

    // 如果没有 Lookup 注解，抽象类无法实例化，会报错 BeanCreationException
    @Lookup(value = "user")
    protected abstract User createUser();

    @Bean(value = "user")
    @Scope(value = SCOPE_PROTOTYPE)
    public User user() {
        User user = new User();
        user.setId((long) new Random().nextInt(100));
        user.setName("aaa");
        return user;
    }
}
