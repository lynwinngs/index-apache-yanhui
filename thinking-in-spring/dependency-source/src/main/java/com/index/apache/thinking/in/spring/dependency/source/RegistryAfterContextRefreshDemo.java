package com.index.apache.thinking.in.spring.dependency.source;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName RegistryAfterContextRefreshDemo
 * @Description 在 context 启动后进行注册的示例
 * @Author xiaoxuezhi
 * @DATE 2020/5/6 13:37
 * @Version 1.0
 **/
public class RegistryAfterContextRefreshDemo {

    @Autowired
    @Qualifier("user2")
    private User user2ByInjection;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        DefaultListableBeanFactory beanFactory = applicationContext.getDefaultListableBeanFactory();
        applicationContext.refresh();

        // 从 ResolvableDependencySourceDemo 示例可知，Resolvable Dependency 可以在 refresh 后注册，可以依赖注入
        // 单一 bean 也可以在 refresh 后进行注册，可以进行依赖查找，但是不能进行依赖注入
        // bean definition 在 refresh 后注册的 bean，可以进行依赖查找，但是不能进行依赖注入
        // 依据：
        // application 在 refresh 时调用 finishBeanFactoryInitialization 方法
        // finishBeanFactoryInitialization 方法会调用 beanFactory 的 freezeConfiguration 方法
        // freezeConfiguration 方法会对当前 beanDefinition 进行备份，并赋值属性
        // 当属性为空时，即没有调用该方法，则获取的 beanDefinition 依然是可添加的
        // 当属性不为空时，则获取的 beanDefinition 集合是备份集合，已经是相对静态的对象了
        // 所以是无法再进行依赖注入的
        beanFactory.registerSingleton("user",new User(1L,"xxz"));

        User user = applicationContext.getBean("user", User.class);

        beanFactory.registerBeanDefinition("user2",
                BeanDefinitionBuilder.genericBeanDefinition(User.class).getBeanDefinition());

        User user2 = applicationContext.getBean("user2", User.class);

        System.out.println(user);
        System.out.println(user2);

        RegistryAfterContextRefreshDemo demo = applicationContext.getBean(RegistryAfterContextRefreshDemo.class);

        // 此处会报错 NoSuchBeanDefinitionException
        System.out.println(demo.user2ByInjection);
    }
}
                                                  