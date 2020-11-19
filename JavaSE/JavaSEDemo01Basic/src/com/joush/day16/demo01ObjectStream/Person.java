package com.joush.day16.demo01ObjectStream;

import java.io.Serializable;

/*
    序列化或者反序列化时会抛出异常，需要继承 Serializable 接口，也叫标记性接口
    要进行序列化的类，必须实现 Serializable 接口，就会给类添加一个标记

    static 静态关键字
        静态优先于非静态加载到内存中（静态优先于对象进入到内存中）
        被 static 修饰的成员变量不能被序列化的，序列话的都是对象
    transient 瞬态关键字
        被 transient 修饰的成员变量，不能被序列化
 */
public class Person implements Serializable {

    // 保证不会出现修改类以后出现的 InvalidClassException 异常
    private static final long serialVersionID = 1l;

    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
