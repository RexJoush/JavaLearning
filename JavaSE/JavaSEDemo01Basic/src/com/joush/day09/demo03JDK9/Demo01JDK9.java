package com.joush.day09.demo03JDK9;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
    jdk9 的新特性
    List, Set, Map 接口添加了静态方法 of，可以一次给集合添加多个元素
        static <E> List<E> of (E...elements)
    使用前提
        1.当前集合中存储的元素个数已经确定，不在改变时使用
    注意事项
        1.of 方法只适用于 List, Set, Map，不适用于接口的实现类，比如 HashMap等
        2.of 方法返回的是一个不能改变的集合，不能再使用 add，put 等方法添加
        3.Set 接口就 Map 在调用 of 方法时，不能有重复元素，否则会抛出异常
 */
public class Demo01JDK9 {

    public static void main(String[] args) {
        List<String> list = List.of("a", "A", "b", "v", "d", "a", "a");
        System.out.println(list); // [a, A, b, v, d, a, a]
        //list.add("w"); // 会抛出不支持操作异常 java.lang.UnsupportedOperationException

        // Set<String> set = Set.of("a", "b", "c", "d", "a");
        // 会抛出非法参数异常 java.lang.IllegalArgumentException

        Set<String> set = Set.of("a", "b", "c", "d");
        System.out.println(set); // [d, c, b, a]

        Map<String, Integer> map = Map.of("张三", 18, "李四", 20);
        System.out.println(map); // {张三=18, 李四=20}
    }

}
