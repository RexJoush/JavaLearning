package com.joush.demo02ticket;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */

/*
    1.创建资源类，定义属性和操作方法
 */

class Ticket {
    // 票数
    private int number = 30;

    // 操作方法
    public synchronized void sale() {
        // 查看是否还有票
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ": sale " + number--);
            System.out.println("还剩下：" + number);
        }
    }
}
public class SaleTicket {

    // 2.创建多个线程，调用资源类的方法
    public static void main(String[] args) {
        // 创建 ticket 对象
        Ticket ticket = new Ticket();

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


