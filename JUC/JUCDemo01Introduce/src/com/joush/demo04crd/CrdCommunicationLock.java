package com.joush.demo04crd;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */
class PrintLock {

    private int flag = 1;

    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    /**
     * 打印方法
     * @param times 打印的次数
     * @param index 当前的轮次
     * @param flag 当前线程要使用的标志位
     * @param next 唤醒的下一个线程的标志位
     */
    public void print(int times, int index, int flag, int next) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            while (this.flag != flag) {
                condition.await();
            }

            for (int i = 1; i <= times; i++) {
                System.out.println(Thread.currentThread().getName() + " times " + i + " 轮次: " + index);
            }
            System.out.println();
            // 修改标志位
            this.flag = next;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
public class CrdCommunicationLock {

    public static void main(String[] args) {
        PrintLock printLock = new PrintLock();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    printLock.print(5, i, 1,2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    printLock.print(10, i, 2, 3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    printLock.print(15, i, 3, 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CC").start();
    }
}
