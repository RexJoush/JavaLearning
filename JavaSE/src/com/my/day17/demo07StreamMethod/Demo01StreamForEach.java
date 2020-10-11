package com.my.day17.demo07StreamMethod;

import java.util.stream.Stream;

/*
    Stream 流中的常用方法 forEach;
    void forEach(Consumer<? super T> action);
    该方法接受一个 Consumer 接口函数，会将每一个流元素交给该函数进行处理
    Consumer 接口是一个消费型函数接口，可以传递 Lambda 表达式，消费数据

    简记，
        forEach 方法，用来遍历流中的数据
        是一个终结方法，不能继续链式调用

 */
public class Demo01StreamForEach {
    public static void main(String[] args) {

        // 获取一个 Stream 流
        Stream<String> stream = Stream.of("张三", "李四", "王五", "赵六");

        /*stream.forEach((String s) -> {
            System.out.println(s);
        });*/

        stream.forEach((s) -> System.out.println(s));

    }
}
