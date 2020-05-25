package com.index.apache.thinking.in.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序启动入口
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/21 22:46
 * @Version： 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.index.apache.thinking.in.redis.structure")
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
