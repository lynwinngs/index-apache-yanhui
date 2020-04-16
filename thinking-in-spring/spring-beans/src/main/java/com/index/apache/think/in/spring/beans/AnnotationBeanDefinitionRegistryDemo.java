package com.index.apache.think.in.spring.beans;

import com.index.apache.think.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName: AnnotationBeanDefinitionRegistryDemo
 * @Description: 注解形式注册 {@link BeanDefinition ｝
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/16 20:28
 * @Version： 1.0
 */
// 3. 使用 @Import 方式注册
//@Import(AnnotationBeanDefinitionRegistryDemo.Config1.class)
public class AnnotationBeanDefinitionRegistryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Config1.class);
        applicationContext.refresh();
        System.out.println(applicationContext.getBean(Config1.class));
//        System.out.println(applicationContext.getBean(LookupIocService.class)); // 必须有 @Bean才能使用
    }

    // 2. 使用 @Component 方式注册
//    @Component
    public static class Config1 {
        // 1. 使用 @Bean 方式注册
//        @Bean
        public LookupIocService lookupIocService() {
            LookupIocService lookupIocService = new LookupIocService();
            lookupIocService.setType("look up1");
            return lookupIocService;
        }
    }

}
