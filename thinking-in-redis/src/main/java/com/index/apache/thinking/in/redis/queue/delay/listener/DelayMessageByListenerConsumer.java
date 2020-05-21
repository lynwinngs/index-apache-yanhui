package com.index.apache.thinking.in.redis.queue.delay.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


/**
 * 延迟消息队列 消费者, 通过监听 key 过期事件，实现延迟消息队列
 * <p>
 * 设置 redis.conf 文件参数，notify-keyspace-events Ex，key 过期会发送事件
 *
 * 具体监听配置，参考配置文件，消息发送通过 Pub/Sub
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/21 22:43
 * @Version： 1.0
 */
@Component
public class DelayMessageByListenerConsumer extends KeyExpirationEventMessageListener {

    public DelayMessageByListenerConsumer(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        long now = System.currentTimeMillis();

        System.out.printf("时间：%d，接收到过期订单：%s.\n", now, expiredKey);
    }
}
