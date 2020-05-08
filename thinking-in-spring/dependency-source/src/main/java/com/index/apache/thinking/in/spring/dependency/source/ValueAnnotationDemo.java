package com.index.apache.thinking.in.spring.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * @ClassName: ValueAnnotationDemo
 * @Description: {@link Value} 注解示例
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/8 20:47
 * @Version： 1.0
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/default.properties",encoding = "GBK")
public class ValueAnnotationDemo {

    @Value("${user.id:-1}")
    private Long id;

    @Value("${usr.name:default}")
    private String name;

    @Value("${user.resource:/default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(ValueAnnotationDemo.class);

        applicationContext.refresh();

        ValueAnnotationDemo demo = applicationContext.getBean(ValueAnnotationDemo.class);

        System.out.println("user.id=" + demo.id);
        System.out.println("user.name=" + demo.name);
        System.out.println("user.resource=" + demo.resource);

        applicationContext.close();
    }
}
