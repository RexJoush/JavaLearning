package com.joush.day11.demo03SetThreadName;

/*
    设置线程名称（了解）
        1.使用 Thread 类中的 setName(名字)方法
            void setName(String name); 改变线程名称
        2.创建一个带参数的构造方法，参数传递线程名称
 */
public class MyThread extends Thread{

    @Override
    public void run() {
        // 获取线程名称
        System.out.println(Thread.currentThread().getName());
    }

    public MyThread() {
    }

    MyThread(String name){
        super(name);
    }
}
