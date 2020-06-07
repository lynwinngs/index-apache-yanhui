package com.index.apache.thinking.in.spring.dependency.injection.setter;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName AnnotationSetterDependencyInjectionDemo
 * @Description 基于注解方式的 setter 依赖注入 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 11:48
 * @Version 1.0
 **/
@Component
public class AnnotationSetterDependencyInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationSetterDependencyInjectionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String location = "classpath:/META-INF/bean-instantiation-context.xml";

        reader.loadBeanDefinitions(location);

        applicationContext.refresh();

        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder);

        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user) {
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }
}
                                                  