package com.index.apache.thinking.in.redis.structure;

import com.index.apache.thinking.in.redis.client.jedis.JedisManager;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 限流器
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/28 12:52
 * @Version 1.0
 **/
public class LimiterDemo {

    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = JedisManager.getJedis();
        for (int i = 0; i < 20; i++) {
            limiter(jedis, "127.0.0.1");
            Thread.sleep(1000L);
        }
    }

    private static void limiter(Jedis jedis, String ip) {
        String s = jedis.get(ip);
        // 设定访问次数 3 次, 这里并没有进行参数校验
        if (s != null && Integer.parseInt(s) > 2) {
            System.out.printf("%s ip 超过访问限制\n", ip);
        } else {
            Transaction multi = jedis.multi();
            multi.incr(ip);
            multi.expire(ip, 5);
            multi.exec();
            System.out.printf("%s ip 执行后续业务代码。。。\n", ip);
        }
    }
}
                                                  