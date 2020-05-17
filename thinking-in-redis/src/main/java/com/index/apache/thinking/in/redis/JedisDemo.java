package com.index.apache.thinking.in.redis;

import redis.clients.jedis.Jedis;

/**
 * jedis 示例
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/15 23:04
 * @Version： 1.0
 */
public class JedisDemo {

    private static String lua = "";

    public static void main(String[] args) {
        Jedis jedis = JedisManager.getJedis();


    }

    private static void setTest(Jedis jedis) {
        jedis.set("hello", "world");

        String hello = jedis.get("hello");

        System.out.println(hello);
    }

    private static void luaTest(Jedis jedis) {
        Object result = jedis.eval(lua);

        System.out.println(result);
    }

    private static void luaShaTest(Jedis jedis) {
        String evalsha = jedis.scriptLoad(lua);

        Object result = jedis.evalsha(evalsha);

        System.out.println(result);

    }
}
