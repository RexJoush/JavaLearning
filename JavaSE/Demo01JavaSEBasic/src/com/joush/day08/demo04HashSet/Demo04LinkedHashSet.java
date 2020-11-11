package com.joush.day08.demo04HashSet;

import java.util.HashSet;
import java.util.LinkedHashSet;

/*
    java.util.LinkedHashSet extends HashSet
    LinkedHashSet 底层是一个哈希表，多了一个链表用来存储元素顺序，保证元素有序
 */
public class Demo04LinkedHashSet {

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        set.add("www");
        set.add("abc");
        set.add("abc");
        set.add("rexjoush");
        System.out.println(set); // [abc, www, rexjoush] 无序，不允许重复


        LinkedHashSet<String> linked = new LinkedHashSet<>();
        linked.add("www");
        linked.add("abc");
        linked.add("abc");
        linked.add("rexjoush");
        System.out.println(linked); // [www, abc, rexjoush] 有序，不允许重复
    }
}
