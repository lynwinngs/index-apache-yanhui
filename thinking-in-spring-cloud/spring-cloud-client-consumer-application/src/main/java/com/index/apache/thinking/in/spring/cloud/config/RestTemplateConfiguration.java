package com.index.apache.thinking.in.spring.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RestTemplateConfiguration
 * @Description RestTemplate 配置类
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 10:38
 * @Version 1.0
 **/
@Configuration
public class RestTemplateConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateWithLoadBalanced() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
                                                  