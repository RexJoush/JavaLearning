package com.joush.day09.demo02MapSet;

import java.util.HashMap;
import java.util.LinkedHashMap;

/*
    java.util.LinkedHashMap<K,V> extends HashMap<K,V>
    map 接口的哈希表和链表实现，具有可预知的迭代顺序
    原理，哈希表加链表记录元素顺序
 */
public class Demo05LinkedHashMap {
    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        map.put("a","a");
        map.put("c","c");
        map.put("b","b");
        map.put("d","d");
        System.out.println(map); // key 不重复，无序

        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("a","a");
        linkedHashMap.put("c","c");
        linkedHashMap.put("b","b");
        linkedHashMap.put("d","d");
        System.out.println(linkedHashMap); // key 不重复，但有序

    }
}
