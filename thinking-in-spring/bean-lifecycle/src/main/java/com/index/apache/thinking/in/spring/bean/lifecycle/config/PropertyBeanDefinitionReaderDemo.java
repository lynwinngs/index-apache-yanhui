package com.index.apache.thinking.in.spring.bean.lifecycle.config;

import com.index.apache.thinking.in.spring.bean.lifecycle.entity.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;

/**
 * 使用 property 加载 bean definition 配置示例
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/15 11:02
 * @Version 1.0
 **/
public class PropertyBeanDefinitionReaderDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader propertiesBeanDefinitionReader = new PropertiesBeanDefinitionReader(beanFactory);
        propertiesBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/user.properties");

        User user = beanFactory.getBean(User.class);

        System.out.println(user);
    }
}
                                                  