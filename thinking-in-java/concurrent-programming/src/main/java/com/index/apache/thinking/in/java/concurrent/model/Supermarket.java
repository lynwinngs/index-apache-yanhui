package com.index.apache.thinking.in.java.concurrent.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: Supermarket
 * @Description: 超市
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/30 22:30
 * @Version： 1.0
 */
public final class Supermarket {

    static {
        shops = Stream.of(
                new Shop("abc"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("ijk"),
                new Shop("jkl"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("ijk"),
                new Shop("jkl"),
                new Shop("klm"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("klm"),
                new Shop("bcd"),
                new Shop("cde"),
                new Shop("def"),
                new Shop("efg"),
                new Shop("fgh"),
                new Shop("ghi"),
                new Shop("hij"),
                new Shop("lmn"))
                .collect(Collectors.toList());
    }

    private static List<Shop> shops;

    public static List<Shop> getShops() {
        return shops;
    }

    private Supermarket() {

    }
}
