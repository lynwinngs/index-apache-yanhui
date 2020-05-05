package com.index.apache.thinking.in.spring.cloud.controller;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import com.index.apache.thinking.in.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

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

    private static final Random random = new Random();

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public boolean add(@RequestParam String name) {
        return userService.saveUser(name);
    }

    @GetMapping("/get")
    public User get(@RequestParam Long id) throws InterruptedException {
        int i = random.nextInt(200);

        System.out.printf("simulate I/O connection timed out [%d] ms\n", i);

        // 如果不抛出异常，则会继续执行
        // 推断 Hystrix 的实现方式应该是 Future
        Thread.sleep(i);

        return userService.getUser(id);
    }
}
                                                  