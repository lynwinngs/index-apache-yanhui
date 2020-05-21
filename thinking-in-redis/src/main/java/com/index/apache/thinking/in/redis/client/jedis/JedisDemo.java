package com.index.apache.thinking.in.redis.client.jedis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * jedis 示例
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/15 23:04
 * @Version： 1.0
 */
public class JedisDemo {

    private static String lua = "local num=redis.call('incr',KEYS[1])\n" +
            "if tonumber(num)==1\n" +
            "then redis.call('expire',KEYS[1],ARGV[1])\n" +
            "return 1\n" +
            "elseif tonumber(num)>tonumber(ARGV[2])\n" +
            "then return 0\n" +
            "else return 1\n" +
            "end";

    public static void main(String[] args) {
        Jedis jedis = JedisManager.getJedis();

        luaShaTest(jedis);
    }

    private static void setTest(Jedis jedis) {
        jedis.set("hello", "world");

        String hello = jedis.get("hello");

        System.out.println(hello);
    }

    private static void luaTest(Jedis jedis) {
        List<String> keys = new ArrayList<>();
        keys.add("ip:limit:127.0.0.1");
        List<String> params = new ArrayList<>();
        params.add("60");
        params.add("10");
        Object result = jedis.eval(lua, keys, params);

        System.out.println(result);
    }

    private static void luaShaTest(Jedis jedis) {
        List<String> keys = new ArrayList<>();
        keys.add("ip:limit:127.0.0.1");
        List<String> params = new ArrayList<>();
        params.add("60");
        params.add("10");
        String evalsha = jedis.scriptLoad(lua);

        Object result = jedis.evalsha(evalsha, keys, params);

        System.out.println(result);

    }
}
