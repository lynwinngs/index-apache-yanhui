package com.index.apache.thinking.in.spring.bean.scope;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

/**
 * TODO
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/13 17:41
 * @Version 1.0
 **/
public class ThreadLocalScopeDemo {

    @Autowired
    @Scope(ThreadLocalScope.THREAD_LOCAL_SCOPE)
    private User theadLocalUser;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.refresh();



        applicationContext.close();
    }
}
                                                  