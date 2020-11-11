package com.joush.day12.demo01Runnable;

/*
    模拟买票案例
    创建3个线程，同时开启，对共享的票进行出售
 */
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

        /*
            会出现卖票重复的问题
         */
    }
}
