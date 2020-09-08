package com.my.day17.demo07StreamMethod;

import java.util.stream.Stream;

/*
    如果需要将流中的元素映射到另一个流中，可以使用 map 方法
    <R> Stream<R> map(Function<? super T, <extends R> mapper);
    改接口需要一个 Function 函数接口，可以将当前流中的 T 类型数据转换为另一种 R 类型数据
    Function 中的抽象方法
        R apply(T t);
 */
public class Demo03StreamMap {

    public static void main(String[] args) {
        // 获取 String 类型的 Stream 流
        Stream<String> stream = Stream.of("1", "2", "3", "4");

        // 使用 map 方法，把字符串类型的整数，转换为 Integer 类型
        Stream<Integer> stream1 = stream.map((s) -> Integer.parseInt(s));
        stream1.forEach((i)-> System.out.println(i));
    }

}
