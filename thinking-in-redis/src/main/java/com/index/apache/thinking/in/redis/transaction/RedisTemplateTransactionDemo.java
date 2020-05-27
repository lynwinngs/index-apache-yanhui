package com.index.apache.thinking.in.redis.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 使用 redisTemplate 操作事务示例
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/27 15:53
 * @Version 1.0
 **/
@Component
public class RedisTemplateTransactionDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        long start = System.currentTimeMillis();

        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.execute((RedisCallback<String>) connection -> {
            for (int i = 0; i < 1000; i++) {
                String key = "key" + i;
                String value = "value" + i;
                connection.set(key.getBytes(), value.getBytes());
                connection.expire(key.getBytes(), 60);
            }
            return null;
        });

        System.out.printf("事务执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
                                                  