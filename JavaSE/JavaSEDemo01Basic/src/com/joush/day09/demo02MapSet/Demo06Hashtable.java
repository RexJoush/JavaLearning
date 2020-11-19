package com.joush.day09.demo02MapSet;

import java.util.HashMap;
import java.util.Hashtable;

/*
    java.util.Hashtable<K,V> implements Map<K,V>
    Hashtable 底层也是一个哈希表，是一个单线程集合，速度较慢
    HashMap 底层是哈希表，但是多线程的集合，速度较快

    HashMap 集合（之前所有集合都）可以存储 null 值，和 null 键
    Hashtable 不能存储 null 值和键
    Hashtable 和 Vector 集合一样，在 jdk 1.2之后被 (HashMap,ArrayList) 取代了
    但 Hashtable 的子类 Properties 依然活跃
    Properties 集合是唯一一个和 I/O 流相结合的集合
 */
public class Demo06Hashtable {

    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        map.put(null,"a");
        map.put("b",null);
        map.put(null,null);
        System.out.println(map); // {null=null, b=null}

        Hashtable table = new Hashtable();
        //table.put(null,"A"); // 抛出异常 java.lang.NullPointerException，不允许空值
    }

}

