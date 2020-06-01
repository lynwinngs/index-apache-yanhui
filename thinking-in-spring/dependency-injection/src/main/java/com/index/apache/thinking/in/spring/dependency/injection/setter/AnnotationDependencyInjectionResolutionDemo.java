package com.index.apache.thinking.in.spring.dependency.injection.setter;

import com.index.apache.thinking.in.spring.dependency.injection.setter.annotation.MyAutowired;
import com.index.apache.thinking.in.spring.dependency.injection.setter.annotation.MyInject;
import com.index.apache.thinking.in.spring.ioc.overview.service.IocService;
import com.index.apache.thinking.in.spring.ioc.overview.service.impl.LookupIocService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName AnnotationDependencyInjectionResolutionDemo
 * @Description 基于注解方式的依赖注入的处理过程 demo
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 11:48
 * @Version 1.0
 **/
@Component
//@Conditional(FalseConditional.class)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    private ApplicationContext applicationContext;

    // ObjectProvider注入：包装 -> Optional注入（包装 -> 普通注入）
    @Autowired
    private ObjectProvider<Optional<LookupIocService>> optionalObjectProvider;

    // ObjectProvider注入：包装 -> 普通注入
    @Autowired
    private ObjectProvider<LookupIocService> lookupIocServiceObjectProvider;

    // Optional注入：包装 -> 普通注入
    @Autowired
    private Optional<LookupIocService> lookupIocServiceOptional;

    // 延迟注入： cglib 动态代理
    @Autowired
    @Lazy
    private LookupIocService lazyLookupIocService;

    // 集合注入： 处理集合类型 -> 查找依赖候选 -> 类型转变
    @Inject
    private List<IocService> iocServiceList;

    // 核心方法：
    // 解析依赖方法 org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency
    // 查找依赖候选 org.springframework.beans.factory.support.DefaultListableBeanFactory#findAutowireCandidates
    // 普通注入：
    // 查找依赖候选 -> 条件过滤 -> 根据名称，类型获取容器中的 bean 实例
    @MyInject
    private IocService iocService;

    @MyAutowired
    private LookupIocService lookupIocService;

    @Bean
    public static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(MyAutowired.class);
        return beanPostProcessor;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);

        String location = "classpath:/META-INF/constructor-dependency-injection.xml";

        reader.loadBeanDefinitions(location);

        applicationContext.refresh();

        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);
//        System.out.println(demo.lookupIocService);

        applicationContext.close();
    }

}
                                                  