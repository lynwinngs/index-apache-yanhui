package com.index.apache.thinking.in.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName: SpringCloudZuulBootstrap
 * @Description: spring cloud zuul网关 引导类
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/4 22:32
 * @Version： 1.0
 */
@SpringBootApplication
@EnableZuulProxy
public class SpringCloudZuulBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulBootstrap.class, args);
    }
}
