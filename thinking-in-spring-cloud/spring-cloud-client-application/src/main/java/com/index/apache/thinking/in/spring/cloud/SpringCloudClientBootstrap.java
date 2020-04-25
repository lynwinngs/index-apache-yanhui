package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName: SpringCloudClientApplication
 * @Description: spring cloud 客户端引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/20 22:56
 * @Version： 1.0
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringCloudClientBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientBootstrap.class, args);
    }
}
