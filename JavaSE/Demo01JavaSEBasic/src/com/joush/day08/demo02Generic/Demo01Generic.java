package com.joush.day08.demo02Generic;

import java.util.ArrayList;
import java.util.Iterator;

public class Demo01Generic {
    public static void main(String[] args) {
        demo02();
    }

    /*
        使用泛型
        好处，避免类型转换的麻烦，运行期异常提升到了编译期异常
        弊端，泛型是什么类型，就只能存储什么类型
     */
    private static void demo02() {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");

        Iterator it = list.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

    }

    /*
        创建集合对象，不使用泛型
        好处，集合不使用泛型，默认就是 Object 类型，可以存储任意数据类型
        弊端，不安全，会引发异常
     */
    private static void demo01() {
        ArrayList list = new ArrayList();
        list.add("AAA");
        list.add(123);

        Iterator it = list.iterator();

        while (it.hasNext()){
            Object obj = it.next();
            System.out.println(obj);

            // 想要使用 String 类的 length 获取长度，
            // 需要向下转型
            String s = (String) obj;
            System.out.println(s.length()); // java.lang.ClassCastException

        }

    }
}
