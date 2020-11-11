package com.joush.day01.demo03ArrayListPrictise;

/*
    题目：
    定义指定格式打印集合元素 (ArrayList作为参数), 使用 {} 括起来，使用 @ 分隔每个元素。
    格式参照 {元素@元素@元素@}
 */

import java.util.ArrayList;

public class Demo03ArrayListPrint {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        printArrayList(list);
    }
    public static void printArrayList(ArrayList<String> list){
        System.out.printf("{");
        for (int i = 0; i < list.size() - 1; i++) {
            System.out.printf(list.get(i) + "@");
        }
        System.out.printf(list.get(list.size()-1) + "}");

    }
}
