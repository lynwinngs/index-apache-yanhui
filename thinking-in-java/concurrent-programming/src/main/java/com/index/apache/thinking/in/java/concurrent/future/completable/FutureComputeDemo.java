package com.index.apache.thinking.in.java.concurrent.future.completable;

import com.index.apache.thinking.in.java.concurrent.model.MethodTemplate;
import com.index.apache.thinking.in.java.concurrent.model.Shop;
import com.index.apache.thinking.in.java.concurrent.model.Supermarket;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: FutureComputeDemo
 * @Description: 传统 future 计算测试
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 23:11
 * @Version： 1.0
 */
public class FutureComputeDemo {

    private static ThreadPoolExecutor executor;

    static {
        executor = new ThreadPoolExecutor(
                Math.min(Supermarket.getShops().size(), 100),
                Integer.MAX_VALUE,
                60000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    public static void main(String[] args) {
        MethodTemplate.invoke(FutureComputeDemo::compute);

        executor.shutdown();
    }

    private static List<String> compute() {
        List<Future<String>> futureList = Supermarket.getShops().stream().map((Shop shop) ->
                executor.submit(() -> String.format("%s price is %.2f", shop.getName(), shop.compute())))
                .collect(Collectors.toList());
        return futureList.stream().map((Future<String> future) ->
        {
            try {
                return future.get();
            } catch (Exception e) {
                return "null";
            }
        }).collect(Collectors.toList());
    }
}
