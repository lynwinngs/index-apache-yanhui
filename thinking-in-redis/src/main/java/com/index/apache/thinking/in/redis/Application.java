package com.index.apache.thinking.in.redis;

import com.index.apache.thinking.in.redis.queue.delay.listener.DelayMessageByListenerApplication;
import com.index.apache.thinking.in.redis.queue.delay.zset.DelayMessageByZSetApplication;
import org.springframework.boot.SpringApplication;

/**
 * 程序启动入口
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/21 22:46
 * @Version： 1.0
 */
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(DelayMessageByListenerApplication.class, args);
    }
}
