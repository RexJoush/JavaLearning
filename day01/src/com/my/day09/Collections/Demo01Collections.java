package com.my.day09.Collections;

import java.util.ArrayList;
import java.util.Collections;

/*
    java.util.Collections 是集合工具类，用来对集合进行操作
        - public static <T> boolean addAll(Collections<T> c, T...elements) 往集合中添加一些元素
        - public static void shuffle(List<?> list) 打乱集合的顺序
 */
public class Demo01Collections {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        // public static <T> boolean addAll(Collections<T> c, T...elements) 往集合中添加一些元素
        Collections.addAll(list,"a","b","c","d","e");
        System.out.println(list); // [a,, b, c, d, e]

        // public static void shuffle(List<?> list) 打乱集合的顺序
        Collections.shuffle(list);
        System.out.println(list); // [e, d, b, a, c]
    }
}
