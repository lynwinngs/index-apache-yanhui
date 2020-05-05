package com.index.apache.thinking.in.spring.cloud.controller;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import com.index.apache.thinking.in.spring.cloud.service.UserFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @ClassName UserController
 * @Description 用户控制器
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 9:44
 * @Version 1.0
 **/
@RestController
public class UserController implements UserFeign {

    /**
     * 使用微服务的服务名做 host 时，必须使用 @LoadBalanced 注解，否则会报错 java.net.UnknownHostException,
     * 因为使用了 @LoadBalanced 的 RestTemplate 会被注入到 org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration
     * RestTemplate 会被放入 org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor 实例
     * 在请求的时候，根据 RestTemplate 是否有拦截器来对 ClientHTTPRequest 进行不同的处理，
     * 没有经过拦截器处理，无法识别以微服务的服务名做 host 的请求
     */
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private UserFeign userFeign;

    @Value("${spring.cloud.client.provider.url}")
    private String serviceUrl;

    @Value("${spring.cloud.client.user.service.url}")
    private String userServiceUrl;

    private static final Random random = new Random();

    @Override
    public boolean add(@RequestParam("name") String name) {
        return restTemplate.postForObject(serviceUrl + userServiceUrl + "/add?name=" + name, null, Boolean.class);
    }

    @Override
    @HystrixCommand(fallbackMethod = "getUserFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")}
    )
    public User get(@RequestParam("id") Long id) {
        return userFeign.get(id);
    }

    private User getUserFallback(Long id) {
        System.out.printf("the user of id-[%d] is null.\n", id);
        return null;
    }

    // HystrixProperty 配置可以到 下面网址查找
    // https://github.com/Netflix/Hystrix/wiki/Configuration#execution.isolation.strategy
    // 也可以到 HystrixCommandProperties 类中查找
    @GetMapping("hello")
    @HystrixCommand(fallbackMethod = "helloFallback",
            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")}
    )
    public String hello() throws InterruptedException {
        int i = random.nextInt(200);

        System.out.printf("simulate I/O connection timed out [%d] ms\n", i);

        // 如果不抛出异常，则会继续执行
        // 推断 Hystrix 的实现方式应该是 Future
        Thread.sleep(i);

        System.out.println("hello world");
        return "hello world";
    }

    private String helloFallback() {
        System.out.println("执行熔断");
        return "fallback";
    }
}
                                                  