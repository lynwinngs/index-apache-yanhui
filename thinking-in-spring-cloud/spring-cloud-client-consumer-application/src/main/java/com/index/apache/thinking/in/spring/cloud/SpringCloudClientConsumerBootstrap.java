package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @ClassName: SpringCloudClientConsumerBootstrap
 * @Description: spring cloud 客户端 消费者 引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/20 22:56
 * @Version： 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class SpringCloudClientConsumerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientConsumerBootstrap.class, args);
    }
}
