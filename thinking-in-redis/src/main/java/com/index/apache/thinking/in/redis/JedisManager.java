package com.index.apache.thinking.in.redis;

import redis.clients.jedis.Jedis;

/**
 * jedis 客户端 管理
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/15 22:58
 * @Version： 1.0
 */
public class JedisManager {

    private static Jedis jedis;

    public static Jedis getJedis(){
        if(jedis == null){
            jedis = new Jedis("127.0.0.1",6379);
        }

        return jedis;
    }
}
