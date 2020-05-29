package com.index.apache.thinking.in.redis.queue.delay.zset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import static com.index.apache.thinking.in.redis.queue.delay.Constants.DELAY_MESSAGE_BY_Z_SET_KEY;

/**
 * 延迟消息队列 生产者
 * <p>
 * 使用 sorted-set 实现延迟消息队列，以到期时间为 score 进行排序
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/21 22:43
 * @Version： 1.0
 */
@Component
public class DelayMessageByZSetProducer {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        while (true) {
            long now = System.currentTimeMillis();

            jedis.zadd(DELAY_MESSAGE_BY_Z_SET_KEY, now + 10000,
                    String.valueOf(now)
            );

            System.out.printf("生产订单:%d,过期时间：%d\n", now, now + 10000);

            Thread.sleep(10000L);
        }
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0/10 * * * * ?")
    public void schedule() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        long now = System.currentTimeMillis();

        zSetOperations.add(DELAY_MESSAGE_BY_Z_SET_KEY, String.valueOf(now),
                now + 10000);

        System.out.printf("生产订单:%d,过期时间：%d\n", now, now + 10000);
    }
}
