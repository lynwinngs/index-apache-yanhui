package com.index.apache.thinking.in.spring.cloud.controller;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import com.index.apache.thinking.in.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private UserService userService;

    @PostMapping("/add")
    public boolean add(@RequestParam String name) {
        return userService.saveUser(name);
    }

    @GetMapping("/get")
    public User get(@RequestParam Long id) {
        return userService.getUser(id);
    }
}
                                                  