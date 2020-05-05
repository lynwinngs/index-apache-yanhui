package com.index.apache.thinking.in.spring.dependency.injection.setter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: MyAutowired
 * @Description: 自定义注入注解
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/5 23:23
 * @Version： 1.0
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAutowired {

    boolean required() default true;

}
