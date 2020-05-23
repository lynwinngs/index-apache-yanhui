package com.index.apache.thinking.in.redis.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 开线程池
 *
 * 避免因开线程池影响批量操作的判断
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/23 22:43
 * @Version： 1.0
 */
@Component
public class AOperationDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        Map<String, String> map = new HashMap<>(256);

        long start = System.currentTimeMillis();

        for (int i = 10000000; i < 10001000; i++) {
            String key = "key" + i;
            String value = "value" + i;
            map.put(key, value);
        }
        operations.multiSet(map);

        System.out.printf("开线程池执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
