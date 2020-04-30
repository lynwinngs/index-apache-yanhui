package com.index.apache.thinking.in.java.concurrent.future.completable;

import com.index.apache.thinking.in.java.concurrent.model.MethodTemplate;
import com.index.apache.thinking.in.java.concurrent.model.Shop;
import com.index.apache.thinking.in.java.concurrent.model.Supermarket;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CompletableFutureComputeDemo
 * @Description: 使用自定义的线程池的 CompletableFuture 计算测试
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 22:29
 * @Version： 1.0
 */
public class CompletableFutureWithCustomExecutorComputeDemo {

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
        MethodTemplate.invoke(CompletableFutureWithCustomExecutorComputeDemo::compute);

        executor.shutdown();
    }

    private static List<String> compute() {
        List<CompletableFuture<String>> completableFutureList = Supermarket.getShops()
                .stream()
                .map((Shop shop) ->
                        CompletableFuture.supplyAsync(
                                () -> String.format("%s price is %.2f",
                                        shop.getName(), shop.compute()), executor))
                .collect(Collectors.toList());

        return completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
