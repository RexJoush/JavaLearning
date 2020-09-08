package com.my.day11.demo01Thread;

/*
    创建多线程的第一种方式，创建 Thread 类的子类
    java.lang.Thread 是描述线程的类，想要实现多线程，必须继承 Thread
 */
public class Demo02Thread {
    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
        for (int i = 0;i < 20; i++){
            System.out.println("main: " + i);
        }
    }
}
