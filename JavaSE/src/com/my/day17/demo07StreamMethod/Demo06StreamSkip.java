package com.my.day17.demo07StreamMethod;

import java.util.stream.Stream;

/*
    Stream 流中的常用方法，skip，用于跳过元素
    skip 方法可以跳过流中的 n 个元素
    Stream<T> skip(long maxSize);
        参数是一个 long 型，如果流长度大于 n，则跳过前 n 个，否则会得到一个长度为 0 的空流
    limit 方法是一个延迟方法，即可以继续链式调用
 */
public class Demo06StreamSkip {
    public static void main(String[] args) {
        String[] arr = {"美羊羊","懒羊羊","灰太狼","沸羊羊"};
        Stream<String> stream = Stream.of(arr);

        // 使用 skip 方法，跳过前两个元素
        stream.skip(2)
                .forEach((s)-> System.out.println(s));
    }
}
