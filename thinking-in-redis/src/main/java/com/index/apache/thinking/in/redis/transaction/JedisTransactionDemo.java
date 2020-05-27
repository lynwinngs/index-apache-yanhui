package com.index.apache.thinking.in.redis.transaction;

import com.index.apache.thinking.in.redis.client.jedis.JedisManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 使用 jedis 客户端操作事务示例
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/27 9:05
 * @Version 1.0
 **/
public class JedisTransactionDemo {

    public static void main(String[] args) {
        Jedis jedis = JedisManager.getJedis();

        long start = System.currentTimeMillis();

        Transaction transaction = jedis.multi();

        for (int i = 0; i < 1000; i++) {
            String key = "key" + i;
            String value = "value" + i;
            transaction.set(key, value);
            transaction.expire(key, 60);
        }

        transaction.exec();

        System.out.printf("事务执行时间：%d ms\n", System.currentTimeMillis() - start);
    }
}
                                                  