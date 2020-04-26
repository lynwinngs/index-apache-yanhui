package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName: SpringCloudRegistryCenterApplication
 * @Description: spring cloud 注册中心引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/25 22:37
 * @Version： 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudRegistryCenterBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudRegistryCenterBootstrap.class, args);
    }
}
