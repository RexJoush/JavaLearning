package com.my.day08.demo01Collection;

import java.util.ArrayList;

/*
    增强 for 循环，底层使用的是迭代器，使用 for 循环的格式简化了迭代器的书写，jdk1.5+

    Collection<E> extends Iterable<E> 所有的单列集合都可以使用
        public interface Iterable<T> 实现这个接口允许对象成为 foreach 语句的目标

    增强 for，用来遍历集合或元素
    格式
        for(集合/数组类型 变量名 : 集合名/数组名){
            System.out.println(变量名);
        }
    注意，增强 for 遍历目标只能是数组或集合，且仅用来遍历
 */
public class Demo03Foreach {
    public static void main(String[] args) {
        demo02();
    }
    // 使用增强 for 遍历集合
    private static void demo02() {
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");

        for (String s : list){
            System.out.println(s);
        }
    }

    // 使用增强 for 遍历数组
    private static void demo01() {
        int[] arr = {1,2,3,4,5};
        for (int i : arr){
            System.out.println(i); // 1 2 3 4 5
        }
    }
}
