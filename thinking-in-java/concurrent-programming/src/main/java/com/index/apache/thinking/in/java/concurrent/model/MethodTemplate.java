package com.index.apache.thinking.in.java.concurrent.model;

import java.util.List;
import java.util.function.Supplier;

/**
 * @ClassName: MethodTemplate
 * @Description: 方法执行模板
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 22:36
 * @Version： 1.0
 */
public class MethodTemplate {

    public static void invoke(Supplier<List<String>> supplier) {
        long start = System.currentTimeMillis();
        System.out.println(supplier.get());
        long end = System.currentTimeMillis();
        System.out.printf("cost %d ms\n", end - start);
    }
}
