package com.joush.demo05collection;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */
public class Demo02Set {

    /*
        HashSet 的线程不安全
     */
    public static void main(String[] args) {

        // 会出现线程不安全
//        Set<String> set = new HashSet<>();

        // 解决方案一，参考 List
        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // 向集合加内容
                set.add(UUID.randomUUID().toString().substring(0, 8));
                // 向集合取内容
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
