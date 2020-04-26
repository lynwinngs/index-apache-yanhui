package com.index.apache.thinking.in.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PropertiesController
 * @Description: properties 属性查询控制器
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/25 21:57
 * @Version： 1.0
 */
@RestController
@RequestMapping("/properties")
@RefreshScope // /actuator/refresh 刷新，需要标注该注解，才能影响这个类的属性
public class PropertiesController {

    @Value("${global.author}")
    private String author;

    @GetMapping("/author")
    public String getAuthor() {
        return author;
    }
}
