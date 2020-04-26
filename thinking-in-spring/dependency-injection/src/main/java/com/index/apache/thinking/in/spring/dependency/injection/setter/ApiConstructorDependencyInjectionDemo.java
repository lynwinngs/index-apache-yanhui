package com.index.apache.thinking.in.spring.dependency.injection.setter;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @ClassName ApiConstructorDependencyInjectionDemo
 * @Description 基于 Java Api 方式的构造器依赖注入 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 11:48
 * @Version 1.0
 **/
public class ApiConstructorDependencyInjectionDemo {

    public static void main(String[] args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        beanDefinitionBuilder.addConstructorArgReference("user-by-static-method");


        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        String location = "classpath:/META-INF/bean-instantiation-context.xml";

        reader.loadBeanDefinitions(location);

        beanFactory.registerBeanDefinition("userHolder",
                beanDefinitionBuilder.getBeanDefinition());

        System.out.println(beanFactory.getBean(UserHolder.class));
    }
}
                                                  