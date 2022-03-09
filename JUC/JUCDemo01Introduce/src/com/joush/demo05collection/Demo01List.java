package com.joush.demo05collection;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */

/*
    List 集合的线程不安全
 */
public class Demo01List {

    public static void main(String[] args) {
        // 会出现线程不安全
//        List<String> list = new ArrayList<>();
        // 方法一：解决线程不安全问题
        List<String> list = new Vector<>();
        // 方法二：解决线程不安全问题
        List<String> list2 = Collections.synchronizedList(new ArrayList<>());

        // 方法三，使用 juc 提供的类
        List<String> list3 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // 向集合加内容
                list.add(UUID.randomUUID().toString().substring(0, 8));
                // 向集合取内容
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }

}
