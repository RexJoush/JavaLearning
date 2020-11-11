package com.joush.day13.demo01ThreadPool;

/*
    2.创建一个类，实现 Runnable 接口，重写 run 方法，设置线程任务
 */
public class RunnableImpl implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "创建了一个新的线程");
    }
}
