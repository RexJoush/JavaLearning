package com.my.day17.demo07StreamMethod;

import java.util.ArrayList;
import java.util.stream.Stream;

/*
    Stream 流中的常用方法，count，统计 Stream 流中的元素个数
    long count();
    count 是一个终结方法，不能再链式调用
 */
public class Demo04StreamCount {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

        Stream<Integer> stream = list.stream();
        long count = stream.count();
        System.out.println(count);

    }
}
