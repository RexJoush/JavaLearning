package com.joush.day08.demo01Collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
    java.util.Iterator 接口，也称迭代器，可以对集合进行遍历
    方法：
        - public boolean hasNext(); 判断集合还有没有下一个元素，有就返回 true，没有则返回 false
        - public E next(); 取出集合中的下一个元素
    Iterator 迭代器是一个借口，无法直接使用，需要使用实现对象
    Collection 接口中有一个方法，iterator() 返回迭代器对象
        - public Iterator<E> iterator(); 返回在此集合上的迭代器对象

    迭代器的使用
        1.使用集合方法 iterator 获取迭代器实现类对象，使用 Iterator 接口接收
        2.使用 Iterator 接口的方法 hasNext() 判断是否有下一个元素
        3.使用 next() 获取集合的下一个元素
 */
public class Demo02Iterator {
    public static void main(String[] args) {
        Collection<String> collection = new ArrayList<>();
        collection.add("梅西");
        collection.add("内马尔");
        collection.add("苏亚雷斯");
        collection.add("皮克");
        collection.add("伊涅斯塔");

        /*
            使用集合方法 iterator 获取迭代器实现类对象，使用 Iterator 接口接收
            注意， Iterator<E> 接口的泛型和集合泛型一样
         */

        Iterator<String> it = collection.iterator();

        /*
            使用循环
         */
        // while，较常用
        while (it.hasNext()){
            System.out.println(it.next());
        }
        System.out.println("=============");

        // for，了解即可，不太常用
        for (Iterator<String> it2 = collection.iterator(); it2.hasNext();){
            System.out.println(it2.next());
        }


        /*
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); // 梅西
        System.out.println(it.next()); // 内马尔
        System.out.println(it.next()); // 苏亚雷斯
        System.out.println(it.next()); // 皮克
        System.out.println(it.next()); // 伊涅斯塔

        System.out.println(it.hasNext()); // false
        it.next(); // 没有元素，抛出 java.util.NoSuchElementException 异常
        */
    }
}
