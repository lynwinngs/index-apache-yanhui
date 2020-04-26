package com.index.apache.thinking.in.spring.ioc.overview.controller;

import com.index.apache.thinking.in.spring.ioc.overview.service.IocService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

/**
 * @ClassName: DependencyController
 * @Description: 依赖控制
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/9 21:36
 * @Version： 1.0
 */
public class DependencyController {

    private Collection<IocService> iocServices;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    public Collection<IocService> getIocServices() {
        return iocServices;
    }

    public void setIocServices(Collection<IocService> iocServices) {
        this.iocServices = iocServices;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
