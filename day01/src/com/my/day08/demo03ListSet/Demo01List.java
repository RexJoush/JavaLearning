package com.my.day08.demo03ListSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
    java.util.List 接口 extends Collection 接口
    List接口特点
        1.有序的集合，存储和取出元素顺序一致
        2.有索引，包含一些带索引的方法
        3.允许存储重复
    List 带索引的方法（特有）
        - public void add(int index, E element) 将指定元素，添加到该集合的指定位置
        - public E get(int index) 返回指定位置的元素
        - public E remove(int index) 删除指定位置的元素，返回被删除的元素
        - public E set(int index, E element) 用指定元素替换指定位置的元素，返回更新前的元素
    注意事项：
        一定要防止索引越界异常
 */
public class Demo01List {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("a");
        System.out.println(list); // [a, b, c, d, a]

        // 1.public void add(int index, E element) 将指定元素，添加到该集合的指定位置
        list.add(3,"su");

        // 2.public E remove(int index) 删除指定位置的元素，返回被删除的元素
        String remove = list.remove(2);
        System.out.println("被移除的： " + remove); // c

        // 3.public E set(int index, E element) 用指定元素替换指定位置的元素，返回更新前的元素
        String a = list.set(4, "A");
        System.out.println("被替换的是" + a); // a

        /*
            List 集合的遍历方式
         */
        // 4.1 普通 for 循环 public E get(int index) 返回指定位置的元素
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 4.2 使用迭代器
        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        // 4.3 增强 for
        for (String s : list) {
            System.out.println(s);
        }


        //String r = list.get(5); // java.lang.IndexOutOfBoundsException 集合会报
        //System.out.println(r);





    }
}
