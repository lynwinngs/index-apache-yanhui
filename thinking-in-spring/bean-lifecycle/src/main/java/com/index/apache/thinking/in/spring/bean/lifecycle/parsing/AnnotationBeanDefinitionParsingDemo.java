package com.index.apache.thinking.in.spring.bean.lifecycle.parsing;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * 注解方式定义的 bean definition 的解析
 *
 * 实际上，该示例演示的是底层如何通过 API 进行注解方式的注册，被注册的类实际上是不需要注解的。
 * 注解的作用在于，使容器能够扫描到需要注册的类，然后对其进行注册
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/16 21:27
 * @Version： 1.0
 */
public class AnnotationBeanDefinitionParsingDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注解方式的 BeanDefinitionReader
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);

        // 之前最常使用的 AnnotationConfigApplicationContext 的 register 方法
        // 实际上，内部还是组合了 AnnotatedBeanDefinitionReader
        // 本质上还是使用 AnnotatedBeanDefinitionReader 的 register 方法
        beanDefinitionReader.register(AnnotationBeanDefinitionParsingDemo.class);

        AnnotationBeanDefinitionParsingDemo demo = beanFactory.getBean(AnnotationBeanDefinitionParsingDemo.class);

        System.out.println(demo);
    }
}
