package com.joush.day12.demo03StaticSynchronized;

import com.joush.day12.demo02Synchronized.RunnableImpl;

public class Demo01Ticket {
    public static void main(String[] args) {
        // 创建 Runnable 接口的实现类对象
        RunnableImpl runnable = new RunnableImpl();

        Thread t0 = new Thread(runnable);
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t0.start();
        t1.start();
        t2.start();
    }
}
