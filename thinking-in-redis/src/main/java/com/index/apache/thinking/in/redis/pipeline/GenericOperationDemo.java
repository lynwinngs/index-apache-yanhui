package com.index.apache.thinking.in.redis.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 普通方式操作
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/23 22:43
 * @Version： 1.0
 */
@Component
public class GenericOperationDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        System.gc();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        long start = System.currentTimeMillis();

        for (int i = 1000; i < 2000; i++) {
            String key = "key" + i;
            String value = "value" + i;
            operations.set(key, value, 60, TimeUnit.SECONDS);
        }

        System.out.printf("普通方式执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
