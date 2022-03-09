package com.joush.demo04crd;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */

class Print {
    // 用于三个线程之间的通信
    private int flag = 1;

    /**
     * 打印方法
     * @param times 打印的次数
     * @param index 当前的轮次
     * @param flag 当前线程要使用的标志位
     * @param next 唤醒的下一个线程的标志位
     */
    public synchronized void print(int times, int index, int flag, int next) throws InterruptedException {
        while (this.flag != flag) {
            this.wait();
        }
        for (int i = 1; i <= times; i++) {
            System.out.println(Thread.currentThread().getName() + " times " + i + " 轮次: " + index);
        }
        System.out.println();
        // 将下一个线程唤醒
        this.flag = next;
        this.notifyAll();
    }

}

// 使用 synchronized 关键字
public class CrdCommunication {

    public static void main(String[] args) {
        Print print = new Print();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    print.print(5, i, 1, 2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    print.print(10, i, 2, 3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    print.print(15, i, 3, 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CC").start();
    }
}
