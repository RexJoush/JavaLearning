package com.joush.demo06SyncLock;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */

// 可重入锁
public class Demo01SyncLock {

    public synchronized void add() {
        add();
    }

    public static void main(String[] args) {


        new Demo01SyncLock().add();

        Object o = new Object();

        new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + "外层");

                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + "中层");

                    synchronized (o) {
                        System.out.println(Thread.currentThread().getName() + "内层");
                    }
                }

            }
        }, "t1").start();
    }
}
