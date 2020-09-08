package com.my.day11.demo01Thread;

/*
    主线程，执行主方法(main)
    单线程程序，java程序中只有一个主线程
    执行从 main 开始，从上到下以此执行
 */
public class Demo01MainThread {
    public static void main(String[] args) {
        Person p1 = new Person("小强");
        p1.run();

        Person p2 = new Person("旺财");
        p2.run();

    }
}
