package com.index.apache.thinking.in.spring.dependency.injection.setter;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * @ClassName UserAnnotation
 * @Description user 注解
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 19:02
 * @Version 1.0
 **/
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
public @interface UserAnnotation {
}
