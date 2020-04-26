package com.index.apache.thinking.in.spring.ioc.overview;

import com.index.apache.thinking.in.spring.ioc.overview.controller.DependencyController;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @ClassName: DependencyInjectionDemo
 * @Description: 依赖注入 demo
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/9 21:33
 * @Version： 1.0
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-injection-context.xml");

        System.out.println("bean factory:" + beanFactory);
        DependencyController bean = beanFactory.getBean(DependencyController.class);
        System.out.println("依赖注入（自定义 bean）:" + bean.getIocServices());

        System.out.println("依赖注入（内建非 bean）:" + bean.getBeanFactory());
        System.out.println("依赖注入（内建非 bean）:" + bean.getApplicationContext());

        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println("依赖注入（内建 bean）:" + environment);
    }
}
