package com.index.apache.thinking.in.java.concurrent.future.completable;

import com.index.apache.thinking.in.java.concurrent.model.MethodTemplate;
import com.index.apache.thinking.in.java.concurrent.model.Shop;
import com.index.apache.thinking.in.java.concurrent.model.Supermarket;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @ClassName: CompletableFutureComputeDemo
 * @Description: CompletableFuture 计算测试
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 22:29
 * @Version： 1.0
 */
public class CompletableFutureComputeDemo {

    public static void main(String[] args) {
        MethodTemplate.invoke(CompletableFutureComputeDemo::compute);
    }

    private static List<String> compute() {
        // 如果合并成一个流，则变成的串行执行
        // 分成两个流才实现并行计算
        List<CompletableFuture<String>> completableFutureList = Supermarket.getShops()
                .stream()
                .map((Shop shop) ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("%s price is %.2f",
                                        shop.getName(), shop.compute())))
                .collect(Collectors.toList());

        return completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
