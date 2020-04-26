package com.index.apache.thinking.in.spring.dependency.injection.setter;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @ClassName XmlSetterDependencyInjectionDemo
 * @Description 基于 xml 方式的 setter 依赖注入 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 10:52
 * @Version 1.0
 **/
public class XmlConstructorDependencyInjectionDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        String location = "classpath:/META-INF/constructor-dependency-injection.xml";

        reader.loadBeanDefinitions(location);

        UserHolder bean = beanFactory.getBean(UserHolder.class);

        System.out.println(bean);
    }


}
                                                  