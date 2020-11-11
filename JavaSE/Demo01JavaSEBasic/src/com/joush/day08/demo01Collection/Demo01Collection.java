package com.joush.day08.demo01Collection;

import java.util.ArrayList;
import java.util.Collection;

/*
    java.util.Collection 接口是所有单列集合最顶层接口，
    定义了所有单列集合共性的方法，任意单列集合都可以使用 Collection 接口中的方法
    方法：
        - public boolean add(E e);  添加元素
        - public void clear();  清空集合
        - public boolean remove(E e);   删除元素
        - public boolean contains(E e); 判断某元素是否存在
        - public boolean isEmpty(); 判断集合是否为空
        - public int size();    返回集合元素个数
        - public Object[] toArray();    将集合变为数组

 */
public class Demo01Collection {
    public static void main(String[] args) {
        // 创建集合对象
        Collection<String> coll = new ArrayList<>();
        System.out.println(coll); // [] 重写了 toString() 方法

        // 1.public boolean add(E e);  添加元素
        boolean b1 = coll.add("张三");
        System.out.println("b1:" + b1);
        coll.add("李四");
        coll.add("王五");
        coll.add("赵六");
        coll.add("田七");
        System.out.println(coll); // [张三, 李四, 王五, 赵六, 田七]

        // 2.public boolean remove(E e);   删除元素
        // 返回 boolean，集合存在该元素，删除元素，返回 true
        // 不存在，就返回 false

        boolean b2 = coll.remove("赵六");
        System.out.println(b2); // true
        boolean b3 = coll.remove("赵四");
        System.out.println(b3); // false
        System.out.println(coll); // [张三, 李四, 王五, 田七]

        // 3.public boolean contains(E e); 判断某元素是否存在
        boolean b4 = coll.contains("李四"); // true
        boolean b5 = coll.contains("赵四"); // false

        // 4.public boolean isEmpty(); 判断集合是否为空
        boolean b6 = coll.isEmpty();
        System.out.println(b6); // false

        // 5.public int size();    返回集合元素个数
        int size = coll.size();
        System.out.println(size); // 4

        // 6.public Object[] toArray(); 将集合变为数组
        Object[] array = coll.toArray();
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        //张三
        //李四
        //王五
        //田七

        // 7.public void clear();  清空集合,但集合还存在
        coll.clear();
        System.out.println(coll); // []
        System.out.println(coll.isEmpty()); // true
    }
}
