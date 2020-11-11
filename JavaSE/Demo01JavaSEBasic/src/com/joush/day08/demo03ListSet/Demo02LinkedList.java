package com.joush.day08.demo03ListSet;

import java.util.LinkedList;

/*
    java.util.LinkedList implements java.util.List
    特点：
        1.底层是链表结构
        2.包含大量操作首尾元素的方法
        3.使用 LinkedList 集合的特有方法，不能使用多态写法

    - public void addFirst(E e); 将指定元素插入列表开头
    - public void addLast(E e); 将制定元素插入列表结尾
    - public E getFirst();  返回列表第一个元素
    - public E getLast();   返回列表的最后一个元素
    - public E removeFirst();   移除并返回列表第一个元素
    - public E removeLast();    移除并返回列表最后一个元素
    - public E pop();   从此列表表示的堆栈处弹出一个元素
    - public void push(E e);    将元素推入此列表所表示的堆栈
    - public boolean isEmpty(); 如果列表为空，返回 true
 */
public class Demo02LinkedList {
    public static void main(String[] args) {
        demo03();
    }

    /*
        public E removeFirst();   移除并返回列表第一个元素
        public E removeLast();    移除并返回列表最后一个元素
        public E pop();   从此列表表示的堆栈处弹出一个元素，相当于 removeFirst
     */
    private static void demo03() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        String s2 = list.removeFirst();
        String s3 = list.removeLast();
    }

    /*
        public E getFirst();  返回列表第一个元素
        public E getLast();   返回列表的最后一个元素
        public E pop();   从此列表表示的堆栈处弹出一个元素
     */
    private static void demo02() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");


        list.clear(); // 注意，此处如果清空列表，那么后面的获取方法均会报异常 java.util.NoSuchElementException

        String first = list.getFirst();
        System.out.println(first); // a
        String last = list.getLast();
        System.out.println(last); // c

    }

    /*
        public void addFirst(E e); 将指定元素插入列表开头
        public void addLast(E e); 将制定元素插入列表结尾
        public void push(E e);    将元素推入此列表所表示的堆栈
     */
    private static void demo01() {
        // 创建 LinkListed 集合对象
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list); // [a,b,c]

        list.addFirst("wwww");
        System.out.println(list); // [www,a,b,c]

        list.push("http");
        System.out.println(list); // [http,www,a,b,c,]

        list.addLast("/login"); // 等效于 add，在末尾追加元素
    }


}
