package com.joush.day11.demo04Runnable;

public class RunnableImpl implements Runnable{
    // 实现 run 方法
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
        }
    }
}
