package com.my.day08.demo03ListSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
    java.util.Set extends Collection
    Set 接口的特点
        1.不允许重复
        2.没有带索引的方法，也不能使用普通的 for 进行循环遍历
    java.util.HashSet 实现了 Set 集合
        1.不允许重复
        2.没有索引
        3.无序集合，存储和取出顺序可能不一致
        4.底层是哈希表结构，查询速度很快
 */
public class Demo03Set {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();

        set.add(1);
        set.add(2);
        set.add(3);
        set.add(1);
        // 使用迭代器遍历
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()){
            System.out.println(it.next()); // [1,2,3,]
        }

        // 使用增强 for 遍历
        for (Integer integer : set) {
            System.out.println(integer); // [1,2,3,]
        }
    }
}
