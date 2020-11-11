package com.joush.day12.demo02Synchronized;

/*
    卖票案例出现了线程安全问题，卖出了不存在的票和重复的票

    解决线程安全问题的第一种方法，使用同步代码块
    格式
        synchronized (锁对象){
            // 可能会出现线程安全的代码 (访问了共享数据)
        }
    注意
        1.同步代码块中的锁对象，可以使用任意对象
        2.但是必须保证多个线程使用的锁对象是同一个
        3.锁对象的作用
            把同步代码块锁住，只让一个线程在同步代码块中进行
 */
public class RunnableImpl implements Runnable {
    // 定义多个线程共享的票源
    private int ticket = 100;

    // 创建一个锁对象
    Object object = new Object();

    @Override
    public void run() {
        // 让买票重复进行
        while (true){
            // 创建同步代码块
            synchronized (object){
                if (ticket > 0){
                    // 提高安全问题，睡眠 10ms
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 票存在，卖票
                    System.out.println(Thread.currentThread().getName() + "-->在卖第" + ticket + "张票");
                    ticket--;
                }
            }
        }
    }
}
