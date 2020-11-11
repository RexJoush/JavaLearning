package com.joush.day17.demo07StreamMethod;

import java.util.stream.Stream;

/*
    Stream 流中常用方法 filter。用于对 Stream 流中的数据进行过滤
    Stream<T> filter(Predicate<? super T> predicate);
    filter 方法的参数 predicate 是一个函数是接口，所以可以传递 Lambda 表达式，对数据进行过滤
    Predicate 中的抽象方法
        boolean test(T t);

 */
public class Demo02StreamFilter {
    public static void main(String[] args) {
        // 创建一个 Stream 流
        Stream<String> stream = Stream.of("张三丰", "张翠山", "赵敏", "周芷若", "张无忌");

        // 对 Stream 过滤，只要姓张的
        Stream<String> stream2 = stream.filter((String name) -> {
            return name.startsWith("张");
        });
        // 遍历
        stream2.forEach((name) -> System.out.println(name));

        /*
            Stream 流属于管道流，只能被使用一次
            第一个 Stream 流使用完毕就会关闭了
            所以第一个 Stream 流使用完就不能再调用方法了
         */

    }
}
