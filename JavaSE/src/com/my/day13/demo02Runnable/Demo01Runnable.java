package com.my.day13.demo02Runnable;

/*
    使用 Runnable 接口方式实现多线程程序
 */
public class Demo01Runnable {
    public static void main(String[] args) {
        // 创建 Runnable 接口的实现类对象
        RunnableImpl run = new RunnableImpl();
        // 创建 Thread 类对象，构造方法中传递 Runnable 接口的实现类
        Thread t = new Thread(run);

        // 开启新线程
        t.start();

        /*
        简化代码使用匿名内部类

         */
        Runnable r = new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "新的线程创建");
            }
        };

        new Thread(r).start();

        /*
            继续简化
         */
        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "新的线程创建");
            }
        }).start();
    }
}
