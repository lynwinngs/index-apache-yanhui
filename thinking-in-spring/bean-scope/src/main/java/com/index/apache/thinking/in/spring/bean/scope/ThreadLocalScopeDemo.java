package com.index.apache.thinking.in.spring.bean.scope;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * ThreadLocal scope demo
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/13 17:41
 * @Version 1.0
 **/
public class ThreadLocalScopeDemo {

    @Bean
    @Scope(ThreadLocalScope.THREAD_LOCAL_SCOPE)
    public User theadLocalUser() {
        return new User(System.nanoTime(), "theadLocalUser");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ThreadLocalScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerScope(ThreadLocalScope.THREAD_LOCAL_SCOPE, new ThreadLocalScope());
        });

        applicationContext.refresh();

        getBeanByLookup(applicationContext);

        applicationContext.close();
    }

    private static void getBeanByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(()->{
                for (int j = 0; j < 3; j++) {
                    User user = applicationContext.getBean(User.class);
                    System.out.printf("thread [%d] user:%s \n", Thread.currentThread().getId(), user);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
                                                  