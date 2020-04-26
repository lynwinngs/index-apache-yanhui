package com.index.apache.thinking.in.spring.config;

import com.index.apache.thinking.in.spring.entity.RedisTemplateMock;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName RedisAutoConfigurationMock
 * @Description Redis 自动装配 模拟
 * @Author xiaoxuezhi
 * @DATE 2020/4/13 14:52
 * @Version 1.0
 **/
public class RedisAutoConfigurationMock {

    @Bean
    public RedisTemplateMock<Object, Object> redisTemplate() {
        RedisTemplateMock<Object, Object> objectObjectRedisTemplateMock = new RedisTemplateMock<>();
        objectObjectRedisTemplateMock.setKey("Object");
        objectObjectRedisTemplateMock.setValue("1");
        return objectObjectRedisTemplateMock;
    }

    @Bean
    public RedisTemplateMock<String, String> stringRedisTemplate() {
        RedisTemplateMock<String, String> objectObjectRedisTemplateMock = new RedisTemplateMock<>();
        objectObjectRedisTemplateMock.setKey("String");
        objectObjectRedisTemplateMock.setValue("2");
        return objectObjectRedisTemplateMock;
    }
}
                                                  