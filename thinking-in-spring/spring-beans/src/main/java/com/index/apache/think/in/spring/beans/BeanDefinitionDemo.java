package com.index.apache.think.in.spring.beans;

import com.index.apache.think.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @ClassName: BeanDefinitionDemo
 * @Description: Bean Definition 示例
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/15 00:11
 * @Version： 1.0
 */
public class BeanDefinitionDemo {

    public static void main(String[] args) {
        // 方式1：通过 BeanDefinitionBuilder 创建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LookupIocService.class);
        beanDefinitionBuilder.addPropertyValue("type","123");
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        // 方式2：通过 AbstractBeanDefinition 创建，原理其实一样
        AbstractBeanDefinition beanDefinition2 = new GenericBeanDefinition();
        beanDefinition2.setBeanClass(LookupIocService.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("type","123");
        beanDefinition2.setPropertyValues(propertyValues);
    }
}
