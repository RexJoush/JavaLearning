package com.joush.demo05collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */
public class Demo03Map {

    /*
        HashMap 的线程不安全，因为 HashMap 是 HashSet 的底层，因此会出现同样的错误
     */
    public static void main(String[] args) {

        // 会出现线程不安全
//        Map<String, String> map = new HashMap<>();

        // 解决方案一，使用 ConcurrentHashMap
        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {

            String key = String.valueOf(i);

            new Thread(() -> {
                // 向集合加内容
                map.put(key, UUID.randomUUID().toString().substring(0, 8));
                // 向集合取内容
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
