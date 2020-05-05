package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @ClassName: SpringCloudConfigServerBootstrap
 * @Description: spring cloud 配置中心服务端引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/20 22:56
 * @Version： 1.0
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class SpringCloudConfigServerBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServerBootstrap.class, args);
    }
}
