package com.index.apache.thinking.in.redis.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 管道方式操作
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/23 22:43
 * @Version： 1.0
 */
@Component
public class PipelineOperationDemo {

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

        long start = System.currentTimeMillis();

        List<Object> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            for (int i = 0; i < 1000; i++) {
                String key = "key" + i;
                String value = "value" + i;
                connection.set(key.getBytes(), value.getBytes(),
                        Expiration.from(60, TimeUnit.SECONDS),
                        RedisStringCommands.SetOption.UPSERT);
            }
            return null;
        });
        System.out.printf("pipeline 方式执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
