package com.joush.day12.demo01Runnable;

/*
    实现卖票案例
 */
public class RunnableImpl implements Runnable {

    // 定义多个线程共享的票源
    private int ticket = 100;

    @Override
    public void run() {
        // 让买票重复进行
        while (true){
            if (ticket > 0){
                // 票存在，买票
                System.out.println(Thread.currentThread().getName() + "-->在卖第" + ticket + "张票");
                ticket--;
            }
        }
    }
}
