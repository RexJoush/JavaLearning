package com.joush.day17.demo07StreamMethod;

import java.util.stream.Stream;

/*
    Stream 流中的常用方法，limit，用于截取流中的元素
    limit 方法可以对流进行截取，只去前 n 个
    Stream<T> limit(long maxSize);
        参数是一个 long 型，如果集合长度大于参数则进行截取，否则不操作
    limit 方法是一个延迟方法，即可以继续链式调用
 */
public class Demo05StreamLimit {
    public static void main(String[] args) {
        String[] arr = {"美羊羊","懒羊羊","灰太狼","沸羊羊"};
        Stream<String> stream = Stream.of(arr);

        // 使用 limit 进行截取
        Stream<String> limit = stream.limit(3);
        limit.forEach((s)-> System.out.println(s));
    }
}
