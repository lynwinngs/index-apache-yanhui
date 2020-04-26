package com.index.apache.thinking.in.spring.ioc.overview.container;

import com.index.apache.thinking.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: AnnotationApplicationContextAsIoCContainer
 * @Description: 注解模式的 {@link ApplicationContext} 作为 IoC 容器
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/12 22:22
 * @Version： 1.0
 */
public class AnnotationApplicationContextAsIoCContainer {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationApplicationContextAsIoCContainer.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBeanDefinitionCount());
        System.out.println(applicationContext.getBean(LookupIocService.class));
    }

    @Bean
    public LookupIocService lookupIocService(){
        LookupIocService lookupIocService = new LookupIocService();
        lookupIocService.setType("lookup");
        return lookupIocService;
    }
}
