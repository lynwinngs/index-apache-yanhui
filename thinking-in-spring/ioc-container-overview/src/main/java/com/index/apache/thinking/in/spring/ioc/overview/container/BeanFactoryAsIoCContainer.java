package com.index.apache.thinking.in.spring.ioc.overview.container;

import com.index.apache.thinking.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @ClassName: BeanFactoryAsIoCContainer
 * @Description: {@link BeanFactory} 作为 IoC 容器
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/12 22:21
 * @Version： 1.0
 */
public class BeanFactoryAsIoCContainer {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String location = "classpath:/META-INF/dependency-lookup-context.xml";
        beanDefinitionReader.loadBeanDefinitions(location);

        System.out.println(beanFactory.getBeanDefinitionCount());
        System.out.println(beanFactory.getBean(LookupIocService.class));
    }
}
