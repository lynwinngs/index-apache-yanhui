package com.index.apache.thinking.in.spring.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: CircularDependenciesDemo
 * @Description: 循环依赖 demo
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/22 21:41
 * @Version： 1.0
 */
public class CircularDependenciesDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(CircularDependenciesDemo.class);

        applicationContext.refresh();
        System.out.println(applicationContext.getBean(ClassA.class));
        System.out.println(applicationContext.getBean(ClassB.class));

        applicationContext.close();
    }

    @Bean
    public ClassA classA(ClassB b) {
        return new ClassA(b);
    }

    @Bean
    public ClassB classB(ClassA a) {
        return new ClassB(a);
    }

    public class ClassA {
        private ClassB b;

        public ClassA(ClassB b) {
            this.b = b;
        }
    }

    public class ClassB {
        private ClassA a;

        public ClassB(ClassA a) {
            this.a = a;
        }
    }
}
