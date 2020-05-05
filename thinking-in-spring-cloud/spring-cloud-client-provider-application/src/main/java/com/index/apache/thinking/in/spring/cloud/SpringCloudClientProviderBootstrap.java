package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: SpringCloudClientProviderBootstrap
 * @Description: spring cloud 客户端 提供者 引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/20 22:56
 * @Version： 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudClientProviderBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientProviderBootstrap.class, args);
    }
}
