package com.joush.day08.demo04HashSet;

import java.util.HashSet;

/*
    HashSet 存储自定义类型
    set 集合存储元素唯一，所以元素必须重写 hashCode 和 equals 方法
    要求：同名同年龄的人只存储一次
 */
public class Demo02HashSetSavePerson {
    public static void main(String[] args) {
        // 创建集合
        HashSet<Person> set = new HashSet<>();
        Person p1 = new Person("小美女",18);
        Person p2 = new Person("小美女",18);
        Person p3 = new Person("小美女",19);

        set.add(p1);
        set.add(p2);
        set.add(p3);

        // 因为没有重写 hashCode方法，所以没有达到目的
        System.out.println(set); // [Person{name='小美女', age=18}, Person{name='小美女', age=19}, Person{name='小美女', age=18}]

    }
}
