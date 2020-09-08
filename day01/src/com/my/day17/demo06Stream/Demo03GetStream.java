package com.my.day17.demo06Stream;

import java.util.*;
import java.util.stream.Stream;

/*
    java.util.stream.Stream<T> 是 jdk1.8 新添加的最常用流接口
    获取一个流非常简单，有以下几种常用方式
        - 所有的 Collection（单列）集合都可以通过 stream 默认的方法获取流
            default Stream<E> stream();
        - Stream 接口的静态方法 of 可以获取数组对应的流
 */
public class Demo03GetStream {
    public static void main(String[] args) {
        // 把集合转成 Stream 流
        ArrayList<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        Set<String> set = new HashSet<>();
        Stream<String> stream2 = set.stream();

        /*
            双列集合转换成 stream
         */
        HashMap<String, String> map = new HashMap<>();
        // 获取键
        Set<String> keySet = map.keySet();
        Stream<String> stream3 = keySet.stream();

        // 获取值
        Collection<String> values = map.values();
        Stream<String> stream4 = values.stream();

        // entry 流
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Stream<Map.Entry<String, String>> stream5 = entries.stream();

        /*
            数组转换为 Stream 流，of方法
         */
        Stream<Integer> stream6 = Stream.of(1, 2, 3, 4, 5);
        // 可变参数可以传递数组
        Integer[] arr = {1,2,3,4,5};
        Stream<Integer> stream7 = Stream.of(arr);

    }
}
