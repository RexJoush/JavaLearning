package com.joush.demo03share;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */
class ShareLock {
    private int number = 0;

    // 创建 Lock 对象
    private Lock lock = new ReentrantLock();

    // 用 lock 创建 condition 对象
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        // 上锁
        lock.lock();

        try {
            // 判断，使用 while 防止虚假唤醒
            while (number != 0) {
                condition.await();
            }
            // 操作
            number++;
            System.out.println(Thread.currentThread().getName() + ": " + number);

            // 通知
            condition.signalAll();
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        // 上锁
        lock.lock();

        try {
            // 判断，使用 while 防止虚假唤醒
            while (number != 1) {
                condition.await();
            }
            // 操作
            number--;
            System.out.println(Thread.currentThread().getName() + ": " + number);

            // 通知
            condition.signalAll();
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}
public class ThreadDemo1Lock {



    public static void main(String[] args) {

        ShareLock share = new ShareLock();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment(); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Increment111").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement(); // -1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Decrement111").start();
    }
}
