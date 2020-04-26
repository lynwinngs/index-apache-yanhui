package com.index.apache.thinking.in.spring.cloud.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @ClassName PropertiesService
 * @Description Properties 属性服务
 * @Author xiaoxuezhi
 * @DATE 2020/4/26 14:18
 * @Version 1.0
 **/
@Service
@RefreshScope // /actuator/refresh 刷新，需要标注该注解，才能影响这个类的属性
public class PropertiesService {

    @Value("${global.author}")
    private String author;

    public String getAuthor() {
        return author;
    }

}
                                                  