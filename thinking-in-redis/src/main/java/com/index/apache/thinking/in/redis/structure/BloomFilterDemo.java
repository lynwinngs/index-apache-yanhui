package com.index.apache.thinking.in.redis.structure;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.*;

/**
 * 布隆过滤器 示例
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/5/29 22:49
 * @Version： 1.0
 */
public class BloomFilterDemo {

    private static long expectedInsertions = 1024L;
    private static double fpp = 0.03D;

    private static BloomFilter<Integer> bloomFilter =
            BloomFilter.create(Funnels.integerFunnel(), expectedInsertions, fpp);

    private static Map<Integer, Integer> mockDatabase = new HashMap<>(2048);

    private static Set<Integer> blackList = new HashSet<>(2048);

    public static void main(String[] args) throws InterruptedException {
        initDatabase();
        Random random = new Random();
        while (true) {
            Thread.sleep(1000L);

            int mockId = random.nextInt(2048);

            if (blackList.contains(mockId)) {
                System.out.printf("%d 在黑名单中\n", mockId);
                continue;
            }

            if (bloomFilter.mightContain(mockId)) {
                if (mockDatabase.containsKey(mockId)) {
                    System.out.printf("%d 在数据库中查询到了\n", mockId);
                } else {
                    System.out.printf("%d 穿过了布隆过滤器，加入黑名单\n", mockId);
                    blackList.add(mockId);
                }
            } else {
                System.out.printf("%d 被布隆过滤器过滤掉了\n", mockId);
            }
        }
    }

    // mock 数据库数据并加载数据库数据到布隆过滤器
    private static void initDatabase() {
        for (int i = 0; i < 1024; i++) {
            mockDatabase.put(i, i);
            bloomFilter.put(i);
        }
    }
}
