package com.joush.day05.demo05Constructor;

/*
    继承关系中，父子类构造方法的特点
    1.子类构造方法中，默认 super(); 调用，所以先进行父类构造，后进行子类构造
    2.可以通过 super 构造调用父类重载构造
    3.super 的父类构造调用，必须是子类构造的第一个语句，不能一个子类构造调用多次 super 构造
 */

public class Demo01Constructor {
    public static void main(String[] args) {
        Zi zi = new Zi(); // 先调用父类，在调用子类
    }
}
