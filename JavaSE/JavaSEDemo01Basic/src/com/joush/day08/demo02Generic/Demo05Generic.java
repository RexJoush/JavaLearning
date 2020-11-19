package com.joush.day08.demo02Generic;

/*
    泛型的通配符 ?
        ? 表示任意类型
    使用方式：
        1.不能创建对象使用
        2.只能作为方法的参数
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Demo05Generic {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");
        printArray(list1);
        printArray(list2);

        // 定义的时候不能使用通配符 ?
        // ArrayList<?> list3 = new ArrayList<?>();
    }

    /*
        定义一个方法，能遍历所有类型的 ArrayList集合
        这时候我们不知道 ArrayList 集合使用什么数据类型，就可以使用泛型通配符
        注意，泛型没有继承概念，不能写 Object，只能写 ?
     */
    public static void printArray(ArrayList<?> list){
        for (Object it : list){
            System.out.println(it);
        }

        Iterator<?> it = list.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

    }

}
