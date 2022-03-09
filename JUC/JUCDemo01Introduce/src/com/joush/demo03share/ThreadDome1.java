package com.joush.demo03share;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */

// 1.创建资源类
class Share {
    // 初始值
    private int number = 0;

    // +1 方法
    public synchronized void increment() throws InterruptedException {
        // 判断
        while (number != 0) { // 如果当前值不为 0.则等待，否则 +1
            this.wait();
        }
        // 操作
        number++;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }
    // -1 方法
    public synchronized void decrement() throws InterruptedException {
        // 判断
        while (number != 1) { // 如果当前值不为 1.则等待，否则 -1
            this.wait();
        }
        // 操作
        number--;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }
}
public class ThreadDome1 {

    public static void main(String[] args) {

        Share share = new Share();

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

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment(); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Increment222").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement(); // -1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Decrement222").start();
    }
}
