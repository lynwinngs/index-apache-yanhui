package com.index.apache.thinking.in.spring.cloud.service;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: UserFeign
 * @Description: user feign 接口
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/5 22:54
 * @Version： 1.0
 */
@FeignClient(value = "provider-application")
public interface UserFeign {

    @PostMapping("/user/add")
    boolean add(@RequestParam("name") String name);

    @GetMapping("/user/get")
    User get(@RequestParam("id") Long id);
}
