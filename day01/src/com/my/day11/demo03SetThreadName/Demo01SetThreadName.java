package com.my.day11.demo03SetThreadName;

public class Demo01SetThreadName {
    public static void main(String[] args) {
        // 开启多线程
        MyThread mt = new MyThread();
        mt.setName("小强");
        mt.start();

        MyThread mt2 = new MyThread("旺财");
        mt2.start();


    }
}
