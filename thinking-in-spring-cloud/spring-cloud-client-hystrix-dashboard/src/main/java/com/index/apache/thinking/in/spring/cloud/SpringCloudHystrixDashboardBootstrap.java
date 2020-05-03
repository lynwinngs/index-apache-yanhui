package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @ClassName: SpringCloudHystrixDashboardBootstrap
 * @Description: spring cloud 断路器看板 引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/20 22:56
 * @Version： 1.0
 */
@SpringBootApplication
@EnableHystrixDashboard
public class SpringCloudHystrixDashboardBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixDashboardBootstrap.class, args);
    }
}
