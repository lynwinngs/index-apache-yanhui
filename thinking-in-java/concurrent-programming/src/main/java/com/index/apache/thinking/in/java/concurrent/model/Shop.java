package com.index.apache.thinking.in.java.concurrent.model;

import java.util.Random;

/**
 * @ClassName: Shop
 * @Description: 商店类
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 22:25
 * @Version： 1.0
 */
public class Shop {

    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double compute() {
        // 模拟 I/O
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 模拟计算
        return new Random().nextDouble() * name.charAt(0) + name.charAt(1);
    }
}
