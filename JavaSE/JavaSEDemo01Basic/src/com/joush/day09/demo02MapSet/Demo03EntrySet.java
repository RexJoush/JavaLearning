package com.joush.day09.demo02MapSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
    Map集合的第二种遍历方法，使用 Entry 对象遍历
    Map集合中的方法：
    Set<Map.Entry<K,V>> entrySet() 返回此映射中包含的映射关系的 Set 视图
    使用步骤：
        1.使用 Map 集合中的方法，entrySet()，把 Map 集合中多个 Entry 对象提取出来，存储到 Set 集合中
        2.遍历 Set 集合，获取没一个 Entry 对象
        3.使用 Entry 对象中的 getKey() 方法和 getValue() 获取键与值

 */
public class Demo03EntrySet {
    public static void main(String[] args) {
        // 创建 Map集合
        Map<String,Integer> map = new HashMap<>();
        map.put("赵丽颖",168);
        map.put("杨颖",165);
        map.put("林志玲",178);

        // 1.使用 Map 集合中的方法，entrySet()，把 Map 集合中多个 Entry 对象提取出来，存储到 Set 集合中
        Set<Map.Entry<String,Integer>> set = map.entrySet();

        // 2.1 使用迭代器 遍历 Set 集合，获取没一个 Entry 对象
        Iterator<Map.Entry<String,Integer>> it = set.iterator();
        while (it.hasNext()){
            Map.Entry<String,Integer> entry = it.next();
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + "=" + value);
        }
        System.out.println("==========");
        // 2.2 使用增强 for 遍历 Set 集合，获取没一个 Entry 对象
        for (Map.Entry<String,Integer> entry: set){
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + "=" + value);
        }

    }
}
