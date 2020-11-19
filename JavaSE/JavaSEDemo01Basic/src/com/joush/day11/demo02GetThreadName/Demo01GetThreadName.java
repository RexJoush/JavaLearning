package com.joush.day11.demo02GetThreadName;

/*
    线程的名称
        主线程：main
        新线程：Thread-0，Thread-1，Thread-2...
 */
public class Demo01GetThreadName {
    public static void main(String[] args) {

        MyThread mt = new MyThread();
        // 执行
        mt.start();

        MyThread mt2 = new MyThread();
        mt2.start();
    }
}
