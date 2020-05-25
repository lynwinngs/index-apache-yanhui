package com.index.apache.thinking.in.redis.structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * 位图数据结构示例
 *
 * 通过位图模拟用户上线
 *
 * note:
 * <ol>
 *     <li>redisTemplate.opsForValue() 中并没有 bitCount 的操作 </li>
 *     <li>如果想使用 bitCount操作统计的话，不能使用 redisTemplate.opsForValue().setBit() </li>
 *     <li>redisTemplate.opsForValue().setBit() 可以通过 redisTemplate.opsForValue().getBit() 查找到</li>
 *     <li>通过 redisTemplate.execute 调用 setBit 后，可以通过 bitCount 统计到 </li>
 *     <li>setBit 时 offset 需要乘以 8，因为 setBit 操作针对 bit，bitCount 操作针对 byte，很坑 </li>
 *     <li>bitCount key start end 的范围统计针对的是 start 到 end 字节上 true 的个数，并非是 start 到 end 位 </li>
 * </ol>
 *
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/25 20:46
 * @Version： 1.0
 */
@Component
public class BitmapDemo {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @PostConstruct
    public void init() {
        String key = "mario";

        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int dayOfYear = date.getDayOfYear();
        System.out.printf("当前是 %d 第 %d 天 \n", year, dayOfYear);

        setBit(key, dayOfYear, true);
        setBit(key, dayOfYear + 1, true);
        setBit(key, dayOfYear + 3, true);

        System.out.printf("昨天 %s 登录了么? %s \n", key, getBit(key, dayOfYear - 1));
        System.out.printf("今天 %s 登录了么? %s \n", key, getBit(key, dayOfYear));

        LocalDate first = LocalDate.of(2020, 5, 1);
        LocalDate last = LocalDate.of(2020, 5, 31);

        Object execute = bitCount(key, first.getDayOfYear(), last.getDayOfYear());

        System.out.printf("5月登录 %s 天 \n", execute);
    }

    private Boolean setBit(String key, long offset, boolean value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.setBit(key.getBytes(), offset * 8, value));
    }

    private Boolean getBit(String key, long offset) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.getBit(key.getBytes(), offset * 8));
    }

    private Long bitCount(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitCount(key.getBytes(), start, end));
    }
}
