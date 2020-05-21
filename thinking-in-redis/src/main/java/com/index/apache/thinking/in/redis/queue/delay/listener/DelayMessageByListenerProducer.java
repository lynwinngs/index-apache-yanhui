package com.index.apache.thinking.in.redis.queue.delay.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.index.apache.thinking.in.redis.queue.delay.Constants.DELAY_MESSAGE_BY_LISTENER_KEY;

/**
 * 延迟消息队列 生产者
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/21 22:43
 * @Version： 1.0
 */
@Component
public class DelayMessageByListenerProducer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();

        long now = System.currentTimeMillis();

        operations.set(DELAY_MESSAGE_BY_LISTENER_KEY + ":" + now
                , String.valueOf(now), 10, TimeUnit.SECONDS);

        System.out.printf("生产订单:%d,过期时间：%d\n", now, now + 10000);
    }
}
