package com.index.apache.think.in.spring.beans;

import com.index.apache.think.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @ClassName: ApiBeanDefinitionRegistryDemo
 * @Description: Api 方式注册 {@link BeanDefinition ｝
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/16 22:32
 * @Version： 1.0
 */
public class ApiBeanDefinitionRegistryDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        register(applicationContext,"lookupIocService");
        register(applicationContext,null);
        applicationContext.refresh();
        System.out.println(applicationContext.getBeansOfType(LookupIocService.class));
    }

    private static void register(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LookupIocService.class);
        beanDefinitionBuilder.addPropertyValue("type", "lookup");

        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }
}
