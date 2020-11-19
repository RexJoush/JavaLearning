package com.joush.day11.demo02GetThreadName;

/*
    获取线程的名称
        1.使用 Thread 类中的方法 getName()
            String getName()，返回线程的名称
        2.可以获取当前线程，使用线程的方法 getName() 获取线程名称
 */
public class MyThread extends Thread{

    // 重写 run 方法
    @Override
    public void run() {
        // 获取线程名称
        Thread t = Thread.currentThread();
        System.out.println(t);
    }
}
