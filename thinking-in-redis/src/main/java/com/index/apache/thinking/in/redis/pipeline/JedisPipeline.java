package com.index.apache.thinking.in.redis.pipeline;

import com.index.apache.thinking.in.redis.client.jedis.JedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;

/**
 * 使用 jedis 客户端操作 pipeline
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/27 9:06
 * @Version 1.0
 **/
public class JedisPipeline {

    public static void main(String[] args) {
        Jedis jedis = JedisManager.getJedis();

        Pipeline pipelined = jedis.pipelined();

        long start = System.currentTimeMillis();

        pipelined.multi();
        for (int i = 0; i < 1000; i++) {
            String key = "key" + i;
            String value = "value" + i;
            pipelined.set(key, value);
            pipelined.expire(key, 60);
        }
        pipelined.exec();

        try {
            pipelined.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("pipeline 方式执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
                                                  