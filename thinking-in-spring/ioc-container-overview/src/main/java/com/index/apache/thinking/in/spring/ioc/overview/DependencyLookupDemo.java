package com.index.apache.thinking.in.spring.ioc.overview;


import com.index.apache.thinking.in.spring.ioc.overview.annotation.Auto;
import com.index.apache.thinking.in.spring.ioc.overview.service.IocService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @ClassName: DependencyLookupDemo
 * @Description: 依赖查找 demo
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/8 22:24
 * @Version： 1.0
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(
                "classpath:/META-INF/dependency-lookup-context.xml");

//        lookupByName(beanFactory);
//        lookupByType(beanFactory);
//        lookupByNameInDelay(beanFactory);
        lookupCollection(beanFactory);
        lookupByAnnotation(beanFactory);
    }

    private static void lookupByName(BeanFactory beanFactory) {
        IocService lookupIocService = (IocService) beanFactory.getBean("lookupIocService");
        System.out.println("按名称查找:" + lookupIocService);
    }

    private static void lookupByType(BeanFactory beanFactory) {
        IocService lookupIocService = beanFactory.getBean(IocService.class);
        System.out.println("按类型查找:" + lookupIocService);
    }

    private static void lookupByNameInDelay(BeanFactory beanFactory) {
        ObjectFactory<IocService> objectFactory = (ObjectFactory<IocService>) beanFactory.getBean("objectFactory");
        IocService lookupIocService = objectFactory.getObject();
        System.out.println("延迟查找:" + lookupIocService);
    }

    private static void lookupCollection(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, IocService> beansOfType = listableBeanFactory.getBeansOfType(IocService.class);
            System.out.println("集合查找:" + beansOfType);
        }
    }

    private static void lookupByAnnotation(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, IocService> beansOfType = (Map) listableBeanFactory.getBeansWithAnnotation(Auto.class);
            System.out.println("注解查找:" + beansOfType);
        }
    }
}
