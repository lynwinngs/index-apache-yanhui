package com.index.apache.thinking.in.spring.bean.lifecycle.config;

import com.index.apache.thinking.in.spring.bean.lifecycle.entity.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 使用 xml 加载 bean definition 配置示例
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/15 11:01
 * @Version 1.0
 **/
public class XmlBeanDefinitionReaderDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/user.xml");

        User user = beanFactory.getBean(User.class);

        System.out.println(user);
    }
}
                                                  