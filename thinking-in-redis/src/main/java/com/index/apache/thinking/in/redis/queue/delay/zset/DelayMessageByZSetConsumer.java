package com.index.apache.thinking.in.redis.queue.delay.zset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

import static com.index.apache.thinking.in.redis.queue.delay.Constants.DELAY_MESSAGE_BY_Z_SET_KEY;

/**
 * 延迟消息队列 消费者
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/21 22:43
 * @Version： 1.0
 */
@Component
public class DelayMessageByZSetConsumer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(cron = "0/1 * * * * ?")
    public void schedule() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        long now = System.currentTimeMillis();

        Set<String> strings = zSetOperations.rangeByScore(DELAY_MESSAGE_BY_Z_SET_KEY, now, now + 1);

        if (CollectionUtils.isEmpty(strings)) {
            System.out.printf("时间：%d，未查询到过期订单\n", now);
        } else {
            System.out.printf("时间：%d，查询到过期订单：%s.\n", now, strings);
            zSetOperations.removeRangeByScore(DELAY_MESSAGE_BY_Z_SET_KEY, now, now + 1);
        }
    }
}
