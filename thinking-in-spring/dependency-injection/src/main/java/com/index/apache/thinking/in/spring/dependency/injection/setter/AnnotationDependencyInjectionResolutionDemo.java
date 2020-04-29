package com.index.apache.thinking.in.spring.dependency.injection.setter;

import com.index.apache.thinking.in.spring.beans.entity.User;
import com.index.apache.thinking.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

/**
 * @ClassName AnnotationDependencyInjectionResolutionDemo
 * @Description 基于注解方式的依赖注入的处理过程 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 11:48
 * @Version 1.0
 **/
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    @Lazy
    private LookupIocService lookupIocService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String location = "classpath:/META-INF/dependency-lookup-context.xml";

        reader.loadBeanDefinitions(location);

        applicationContext.refresh();

        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);
        System.out.println(demo.lookupIocService);

        applicationContext.close();
    }

}
                                                  