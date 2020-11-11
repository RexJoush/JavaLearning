package com.joush.day12.demo04Unlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    卖票案例出现了线程安全问题，卖出了不存在的票和重复的票

    解决线程安全问题的第一种方法，使用 Lock 锁
    java.util.concurrent.locks.Lock 接口
    Lock 接口实现了比使用 synchronize 方法和语句可获得的更广泛的锁定操作
    Lock 接口中的方法
        void lock(); 获取锁
        void unlock(); 释放锁
    java.util.concurrent.locks.ReentrantLock implements Lock

    使用步骤
        1.在成员位置创建一个 ReentrantLock 对象
        2.在可能出现安全问题的代码前调用 Lock 接口中的方法 lock() 获取锁
        3.在可能出现安全问题的代码后，调用 Lock 接口中的方法 unlock() 释放锁
 */
public class RunnableImpl implements Runnable {
    // 定义多个线程共享的票源
    private int ticket = 100;

    // 1.在成员位置创建一个 ReentrantLock 对象
    Lock l = new ReentrantLock();

    @Override
    public void run() {
        // 让买票重复进行
        while (true){

            // 2.在可能出现安全问题的代码前调用 Lock 接口中的方法 lock() 获取锁
            l.lock();

            // 创建同步代码块
            if (ticket > 0){
                // 提高安全问题，睡眠 10ms
                try {
                    Thread.sleep(10);
                    // 票存在，卖票
                    System.out.println(Thread.currentThread().getName() + "-->在卖第" + ticket + "张票");
                    ticket--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    // 无论程序是否异常，均会释放锁
                    // 3.在可能出现安全问题的代码后，调用 Lock 接口中的方法 unlock() 释放锁
                    l.unlock();
                }
            }
        }
    }
}
