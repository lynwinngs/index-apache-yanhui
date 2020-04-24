package com.index.apache.think.in.spring.dependency.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName SafetyDependencyLookupDemo
 * @Description 安全性依赖查找 demo
 * 这里安全性，指的系统异常的安全性，非安全的方法会抛出异常，需要进行异常处理
 * @Author xiaoxuezhi
 * @DATE 2020/4/24 11:12
 * @Version 1.0
 **/
public class SafetyDependencyLookupDemo {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DelayDependencyLookupDemo.class);

        applicationContext.refresh();

        /* 单一查找 */
        // BeanFactory getBean 不安全
        testBeanFactoryGetBean(applicationContext);
        // ObjectFactory getObject 不安全
        testObjectFactoryGetObject(applicationContext);
        // ObjectProvider getIfAvailable 安全
        testObjectProviderGetIfAvailable(applicationContext);

        /* 集合查找 */
        // ListableBeanFactory getBeansOfType 安全
        testListableBeanFactoryGetBeansOfType(applicationContext);
        // ObjectProvider stream 安全
        testObjectProviderStream(applicationContext);

        applicationContext.close();
    }

    private static void testBeanFactoryGetBean(AnnotationConfigApplicationContext applicationContext) {
        printGetBeanTrace("bean factory getBean",
                () -> applicationContext.getBean(User.class));
    }

    private static void testObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
        ObjectFactory<User> objectFactory = applicationContext.getBeanProvider(User.class);
        printGetBeanTrace("object factory getObject",
                objectFactory::getObject);
    }

    private static void testObjectProviderGetIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printGetBeanTrace("object provider getIfAvailable",
                objectProvider::getIfAvailable);
    }

    private static void testListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext applicationContext) {
        DefaultListableBeanFactory defaultListableBeanFactory = applicationContext.getDefaultListableBeanFactory();
        printGetBeanTrace("listable bean factory getBeansOfType",
                () -> defaultListableBeanFactory.getBeansOfType(User.class));
    }

    private static void testObjectProviderStream(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
        printGetBeanTrace("object provider stream",
                objectProvider::stream);
    }

    private static void printGetBeanTrace(String source, Runnable r) {
        System.out.println("==================");
        System.out.println("source: " + source);
        try {
            r.run();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
                                                  