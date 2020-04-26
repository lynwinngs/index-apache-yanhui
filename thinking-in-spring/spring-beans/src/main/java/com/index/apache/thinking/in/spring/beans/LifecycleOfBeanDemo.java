package com.index.apache.thinking.in.spring.beans;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * @ClassName: LifecycleOfBeanDemo
 * @Description: bean 的生命周期
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/20 20:43
 * @Version： 1.0
 */
public class LifecycleOfBeanDemo {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LifecycleOfBeanDemo.class);

        System.out.println("准备初始化容器");
        applicationContext.refresh();
        System.out.println("容器初始化完成");

        // 获取 bean 的名称是通过 bean definition，因此不需要 bean 初始化，只需要 BeanFactory 创建完成即可获得
        // 此处使用 lazy bean 来证明不需要初始化即可获得 bean 名称
        // 过程：
        // org.springframework.context.support.AbstractApplicationContext#refresh ->
        // org.springframework.context.support.AbstractApplicationContext#obtainFreshBeanFactory ->
        // getBeanNamesForType 此时已可以获得 bean name
        System.out.println(Arrays.asList(applicationContext.getBeanNamesForType(LifecycleOfBeanDemo.class)));

        System.out.println(applicationContext.getBean(User.class));

        System.out.println("准备关闭容器");
        applicationContext.close();
        System.out.println("容器关闭完成");

        System.gc();

        Thread.sleep(1000L);
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
//    @Lazy
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("aaa");
        return user;
    }
}
