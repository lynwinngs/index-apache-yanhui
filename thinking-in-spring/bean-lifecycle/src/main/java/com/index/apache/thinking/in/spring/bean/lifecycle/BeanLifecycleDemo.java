package com.index.apache.thinking.in.spring.bean.lifecycle;

import com.index.apache.thinking.in.spring.bean.lifecycle.config.MyInstantiationAwareBeanPostProcessor;
import com.index.apache.thinking.in.spring.bean.lifecycle.entity.SuperUser;
import com.index.apache.thinking.in.spring.bean.lifecycle.entity.User;
import com.index.apache.thinking.in.spring.bean.lifecycle.entity.UserHolder;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * TODO
 *
 * @Author xiaoxuezhi
 * @DATE 2020/6/4 9:32
 * @Version 1.0
 **/
public class BeanLifecycleDemo implements InstantiationAwareBeanPostProcessor {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());

        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/user.xml");

        User user = beanFactory.getBean("user", User.class);

        System.out.println(user);

        SuperUser superUser = beanFactory.getBean("superUser", SuperUser.class);

        System.out.println(superUser);

        UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);

        System.out.println(userHolder);
    }


}
                                                  