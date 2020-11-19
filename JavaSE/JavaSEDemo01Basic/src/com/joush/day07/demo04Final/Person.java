package com.joush.day07.demo04Final;

/*
    对于成员变量来说，如果使用 final 修饰，那么这个变量也是不可变
    1.由于成员变量具有默认值，所以用了 final 必须手动赋值，不会给默认值了
    2.对于 final 的成员变量，要么直接赋值，要么通过构造方法赋值，二选一
    3.必须保证类中的所有重载构造方法，都会对 final 变量进行赋值
 */
public class Person {
    private final String name;

    public Person() {
        name = "关晓彤";
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
}
