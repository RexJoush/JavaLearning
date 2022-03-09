package com.joush.demo02ticket;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */

// 创建资源类
class LTicket {
    // 票数
    private int number = 30;

    // 创建可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    public void sale() {
        // 上锁
        lock.lock();

        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ": sale " + number-- + "剩余: " + number);
            }
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
}
public class LSaleTicket {

    // 2.创建多个线程，调用资源类的方法
    public static void main(String[] args) {
        // 创建 ticket 对象
        LTicket ticket = new LTicket();

        // 创建三个线程
        new Thread(() -> {
            // 调用卖票的方法
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            // 调用卖票的方法
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            // 调用卖票的方法
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "CC").start();
    }
}
