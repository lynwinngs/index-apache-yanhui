package com.index.apache.thinking.in.redis.pubsub;

import com.index.apache.thinking.in.redis.client.jedis.JedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Sub 订阅者 示例
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/31 16:43
 * @Version 1.0
 **/
public class Subscriber {

    public static void main(String[] args) {
        Jedis jedis = JedisManager.getJedis();

        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(message);
            }
        }, "mychannel");
    }
}
                                                  