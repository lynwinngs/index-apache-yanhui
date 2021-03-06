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
