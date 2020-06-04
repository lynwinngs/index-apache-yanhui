package com.index.apache.thinking.in.spring.bean.lifecycle.config;

import com.index.apache.thinking.in.spring.bean.lifecycle.entity.SuperUser;
import com.index.apache.thinking.in.spring.bean.lifecycle.entity.User;
import com.index.apache.thinking.in.spring.bean.lifecycle.entity.UserHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @Author xiaoxuezhi
 * @DATE 2020/6/4 9:35
 * @Version 1.0
 **/
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass == User.class && ObjectUtils.nullSafeEquals("user", beanName)) {
            return new User(3L, "hello");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == SuperUser.class && ObjectUtils.nullSafeEquals("superUser", beanName)) {
            SuperUser superUser = (SuperUser) bean;
            superUser.setId(4L);
            superUser.setName("world");
            superUser.setCity("beijing");
            return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (bean.getClass() == UserHolder.class && ObjectUtils.nullSafeEquals("userHolder", beanName)) {
            MutablePropertyValues mpv = new MutablePropertyValues();
            mpv.addPropertyValue("description", "user holder v1");
            return mpv;
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
                                                  