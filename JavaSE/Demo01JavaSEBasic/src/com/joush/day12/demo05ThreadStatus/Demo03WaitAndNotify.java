package com.joush.day12.demo05ThreadStatus;

/*
    进入到 TimeWaiting(计时等待)有两种方法
    1.使用 sleep(long m) 方法，毫秒值结束进入 Runnable/Blocked 状态
    2.使用 wait(long m) 方法。wait 方法在毫秒值结束之后，还没有被 notify 唤醒，就会自动醒来

    唤醒的方法
        1.notify() 唤醒单个线程
        2.notifyAll() 唤醒所有线程
 */
public class Demo03WaitAndNotify {
    public static void main(String[] args) {
        // 创建锁对象
        Object obj = new Object();
        // 创建一个顾客1线程
        new Thread(){
            @Override
            public void run() {
                while (true){
                    // 保证同步
                    synchronized(obj){
                        System.out.println("1告知老板包子的种类和数量");
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后的代码
                        System.out.println("包子好了，1吃");
                    }
                }
            }
        }.start();

        // 创建一个顾客2线程
        new Thread(){
            @Override
            public void run() {
                while (true){
                    // 保证同步
                    synchronized(obj){
                        System.out.println("2告知老板包子的种类和数量");
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 唤醒之后的代码
                        System.out.println("包子好了，2吃");
                    }
                }
            }
        }.start();


        // 创建老板线程
        new Thread(){
            @Override
            public void run() {
                while (true){
                    // 花5秒做包子
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (obj){
                        System.out.println("老板做好了，告知顾客来吃");
                        obj.notifyAll();
                    }
                }
            }
        }.start();
    }
}
