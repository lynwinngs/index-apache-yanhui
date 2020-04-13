package com.index.apache.think.in.spring.beans.factory;

import com.index.apache.think.in.spring.config.RedisAutoConfigurationMock;
import com.index.apache.think.in.spring.entity.RedisTemplateMock;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;

/**
 * @ClassName BeanFactoryDemo
 * @Description bean factory 示例
 * @Author xiaoxuezhi
 * @DATE 2020/4/13 14:50
 * @Version 1.0
 **/
public class BeanFactoryDemo {

    private RedisTemplateMock<String, String> redisTemplate;

    public static void main(String[] args) throws NoSuchFieldException {
        // 创建 ApplicationContext 上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将 RedisAutoConfigurationMock 注册为 {@link Configuration}
        applicationContext.register(RedisAutoConfigurationMock.class);
        // 启动上下文
        applicationContext.refresh();

        // 通过 name 获取 bean
        Object redisTemplate = applicationContext.getBean("redisTemplate");
        System.out.println(redisTemplate);

        // BeanFactory#getBeanProvider(ResolvableType) 获取 ObjectFactory
        Field field = BeanFactoryDemo.class.getDeclaredField("redisTemplate");
        ResolvableType resolvableType = ResolvableType.forField(field);
        ObjectProvider<Object> beanProvider = applicationContext.getBeanProvider(resolvableType);
        System.out.println(beanProvider.getObject());
    }
}
                                                  