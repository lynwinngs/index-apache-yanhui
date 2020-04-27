package com.index.apache.thinking.in.spring.cloud.controller;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName UserController
 * @Description 用户控制器
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 9:44
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${spring.cloud.client.provider.url}")
    private String serviceUrl;

    @Value("${spring.cloud.client.user.service.url}")
    private String userServiceUrl;

    @PostMapping("/add")
    public boolean add(@RequestParam String name) {
        return restTemplate.postForObject(serviceUrl + userServiceUrl + "/add?name=" + name, null, Boolean.class);
    }

    @GetMapping("/get")
    public User get(@RequestParam Long id) {
        return restTemplate.getForObject(serviceUrl + userServiceUrl + "/get?id=" + id, User.class);
    }
}
                                                  