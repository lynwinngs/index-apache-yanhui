package com.index.apache.thinking.in.java.concurrent.future.completable;

import com.index.apache.thinking.in.java.concurrent.model.MethodTemplate;
import com.index.apache.thinking.in.java.concurrent.model.Shop;
import com.index.apache.thinking.in.java.concurrent.model.Supermarket;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ParallelStreamComputeDemo
 * @Description: 并行流计算测试
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 22:29
 * @Version： 1.0
 */
public class ParallelStreamComputeDemo {

    public static void main(String[] args) {
        MethodTemplate.invoke(ParallelStreamComputeDemo::compute);
    }

    private static List<String> compute() {
        return Supermarket.getShops().parallelStream()
                .map((Shop shop) -> String.format("%s price is %.2f", shop.getName(), shop.compute()))
                .collect(Collectors.toList());
    }
}
