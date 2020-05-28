package com.index.apache.thinking.in.redis.client.jedis;

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
            jedis = new Jedis("192.168.186.146",6379);
        }
        return jedis;
    }
}
