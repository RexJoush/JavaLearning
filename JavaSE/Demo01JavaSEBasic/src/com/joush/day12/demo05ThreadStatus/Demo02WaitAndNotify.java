package com.joush.day12.demo05ThreadStatus;


/*
    等待唤醒，线程之间的通信
        创建一个顾客线程，告知老板包子的种类和数量，调用 wait 方法，放弃 cpu 执行，进入 WAITING 状态
        创建一个老板线程，花5秒做包子，调用 notify 方法，唤醒顾客吃包子
    注意
        顾客和老板线程必须使用同步代码块包起来，保证等待和唤醒只能有一个在执行
        同步使用锁对象必须唯一，只有锁对象能调用 wait 和 notify 方法
    Object 类中的方法
    void wait(); 在其他线程调用此对象的 notify 方法或 notifyAll() 方法前，导致当前线程等待
    void notify(); 唤醒再次对象监视器上等待的单个线程，唤醒之后继续会执行 wait 后的代码
 */
public class Demo02WaitAndNotify {
    public static void main(String[] args) {
        // 创建锁对象
        Object obj = new Object();
        // 创建一个顾客线程
        new Thread(){
            @Override
            public void run() {
                // 保证同步
                synchronized(obj){
                    System.out.println("告知老板包子的种类和数量");
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 唤醒之后的代码
                    System.out.println("包子好了，吃");
                }
            }
        }.start();


        // 创建老板线程
        new Thread(){
            @Override
            public void run() {
                // 花5秒做包子
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj){
                    System.out.println("老板做好了，告知顾客来吃");
                    obj.notify();
                }
            }
        }.start();

    }
}
