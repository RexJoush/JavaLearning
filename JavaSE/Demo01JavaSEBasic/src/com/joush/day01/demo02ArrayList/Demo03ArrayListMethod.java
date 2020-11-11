package com.joush.day01.demo02ArrayList;


/*
    ArrayList 当中常用的方法

    public boolean add(E e); 向集合里添加元素

    对于 ArrayList 集合来说，add 添加动作一定是成功的，返回值可用可不用。
        对于其他集合来说，add添加动作不一定成功，所以返回值代表添加是否成功

    public E get(int index); 从集合中取元素

    public E remove(int index); 从集合中删除元素

    public int size(); 获取集合长度，返回值是包含的元素个数

 */


import java.util.ArrayList;

public class Demo03ArrayListMethod {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        // 添加元素 add
        boolean success = list.add("柳岩");
        System.out.println(list); // [柳岩]
        System.out.println("添加的动作是否成功" + success); // true

        list.add("高圆圆");
        list.add("赵又廷");
        list.add("李小璐");
        list.add("贾乃亮");
        System.out.println(list); // [柳岩, 高圆圆, 赵又廷, 李小璐, 贾乃亮]

        // 获取元素： get，索引从0开始。
        String name = list.get(2);
        System.out.println("第二号索引位置：" + name); // 赵又廷

        // 删除元素： remove, 索引从0开始。
        String whoRemoved = list.remove(3);
        System.out.println("被删除的人是：" + whoRemoved); // 李小璐

        // 获取长度，即集合元素个数
        int size = list.size();
        System.out.println("集合长度：" + size); // 4
    }

}
