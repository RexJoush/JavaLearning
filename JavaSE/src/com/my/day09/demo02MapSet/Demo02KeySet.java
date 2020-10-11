package com.my.day09.demo02MapSet;

import java.util.*;

/*
    Map 集合的第一章遍历方式，通过键找值
    Map 集合中的方法
        Set<K> keySet() 返回此映射中包含的键的 Set 视图
    实现步骤
        1.使用 Map 集合中的方法 keySet()，把 Map 集合中的所有 key 取出来，存在 Set 集合中
        2.遍历 set 集合，获取 Map 集合中的每一个 key
        3.通过 Map 集合中的方法 get(Object key)，通过 key 找到 value；
 */
public class Demo02KeySet {
    public static void main(String[] args) {
        // 创建 Map集合
        Map<String,Integer> map = new HashMap<>();
        map.put("赵丽颖",168);
        map.put("杨颖",165);
        map.put("林志玲",178);

        // 1.通过 keySet 方法获取 key
        Set<String> set = map.keySet();

        // 2.1 遍历 set,迭代器
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String key = it.next();
            System.out.println(key + "=" + map.get(key));
        }

        // 2.2 遍历，增强 for
        for (String key : map.keySet()){
            System.out.println(key + "=" + map.get(key));
        }
    }
}
