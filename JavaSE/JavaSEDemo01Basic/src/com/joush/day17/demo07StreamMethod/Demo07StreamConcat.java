package com.joush.day17.demo07StreamMethod;

import java.util.stream.Stream;

/*
    Stream 流中的常用方法，concat，用于合并两个流
    concat 方法可以合并两个流，concat 是一个静态方法
    static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b);
 */
public class Demo07StreamConcat {
    public static void main(String[] args) {
        String[] arr = {"美羊羊","懒羊羊","灰太狼","沸羊羊"};
        Stream<String> stream1 = Stream.of(arr);

        Stream<String> stream2 = Stream.of("张三丰", "张翠山", "赵敏", "周芷若", "张无忌");

        // 使用 concat 连接
        Stream.concat(stream1,stream2)
                .forEach((s)-> System.out.println(s));

    }
}
