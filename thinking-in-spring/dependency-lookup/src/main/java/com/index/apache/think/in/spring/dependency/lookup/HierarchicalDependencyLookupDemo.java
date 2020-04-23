package com.index.apache.think.in.spring.dependency.lookup;

import com.index.apache.think.in.spring.beans.entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName HierarchicalDependencyLookupDemo
 * @Description 层次性依赖查找
 * @Author xiaoxuezhi
 * @DATE 2020/4/23 13:53
 * @Version 1.0
 **/
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(HierarchicalDependencyLookupDemo.class);

        applicationContext.refresh();

        // HierarchicalBeanFactory -> ConfigurableBeanFactory -> ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        System.out.println("当前 bean factory 的双亲：" + beanFactory.getParentBeanFactory());

        ConfigurableListableBeanFactory parentBeanFactory = createParentBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前 bean factory 的双亲：" + beanFactory.getParentBeanFactory());

        /* 全局 */
        // 子的 bean，父没有，子有
        containBean(parentBeanFactory, "user");
        containBean(beanFactory, "user");

        // 父的 bean（子没有覆盖），父有，子有，bean 相同
        containBean(parentBeanFactory, "injectionIocService");
        containBean(beanFactory, "injectionIocService");
        System.out.println("父容器的 injectionIocService bean: " + parentBeanFactory.getBean("injectionIocService"));
        System.out.println("子容器的 injectionIocService bean: " + beanFactory.getBean("injectionIocService"));

        // 父子有相同名称的 bean（子有覆盖）, 父取得父的，子取得子的
        System.out.println("父容器的 lookupIocService bean: " + parentBeanFactory.getBean("lookupIocService"));
        System.out.println("子容器的 lookupIocService bean: " + beanFactory.getBean("lookupIocService"));

        /* 本地 */
        // 父的 bean（子没有覆盖），父有，子没有
        containLocalBean(parentBeanFactory, "injectionIocService");
        containLocalBean(beanFactory, "injectionIocService");

        /* 使用本地方法 手写 自定义全局方法 */
        System.out.println("自定义全局方法，父容器是否包含的 user bean:  " +
                customContainBean(parentBeanFactory, "user"));
        System.out.println("自定义全局方法，子容器是否包含的 user bean:  " +
                customContainBean(beanFactory, "user"));

        System.out.println("自定义全局方法，父容器是否包含的 injectionIocService bean:  " +
                customContainBean(parentBeanFactory, "injectionIocService"));
        System.out.println("自定义全局方法，子容器是否包含的 injectionIocService bean:  " +
                customContainBean(beanFactory, "injectionIocService"));

        applicationContext.close();
    }

    @Bean
    public User user() {
        return new User();
    }

    @Bean
    String lookupIocService() {
        return "child's lookupIocService";
    }

    private static boolean customContainBean(HierarchicalBeanFactory beanFactory, String beanName) {
//        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
//        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
//            HierarchicalBeanFactory hierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
//            if(customContainBean(hierarchicalBeanFactory,beanName)){
//                return true;
//            }
//        }
//        return beanFactory.containsLocalBean(beanName);

        if(beanFactory.containsLocalBean(beanName)){
            return true;
        }else {
            BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
            if (parentBeanFactory instanceof HierarchicalBeanFactory) {
                HierarchicalBeanFactory hierarchicalBeanFactory = (HierarchicalBeanFactory) parentBeanFactory;
                return customContainBean(hierarchicalBeanFactory,beanName);
            }else {
                return false;
            }
        }
    }

    private static void containBean(BeanFactory beanFactory, String beanName) {
        System.out.printf("bean factory [%s] 包含的 bean [name: %s] %s\n", beanFactory, beanName,
                beanFactory.containsBean(beanName));
    }

    private static void containLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("bean factory [%s] 包含的本地 bean [name: %s] %s\n", beanFactory, beanName,
                beanFactory.containsLocalBean(beanName));
    }

    private static ConfigurableListableBeanFactory createParentBeanFactory() {
        DefaultListableBeanFactory listableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(listableBeanFactory);
        beanDefinitionReader.loadBeanDefinitions("META-INF/dependency-lookup-context.xml");

        return listableBeanFactory;
    }
}
                                                  