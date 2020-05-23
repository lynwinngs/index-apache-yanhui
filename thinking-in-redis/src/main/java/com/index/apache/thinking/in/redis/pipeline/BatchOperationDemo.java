package com.index.apache.thinking.in.redis.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量方式操作
 *
 * TODO: 为什么批量要比单次循环操作要慢？？？
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/23 22:43
 * @Version： 1.0
 */
@Component
public class BatchOperationDemo {

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

        Map<String, String> map = new HashMap<>(256);

        long start = System.currentTimeMillis();

        for (int i = 2000; i < 3000; i++) {
            String key = "key" + i;
            String value = "value" + i;
            map.put(key, value);
        }
        operations.multiSet(map);

        System.out.printf("批量方式执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
