package com.joush.day11.demo01Thread;

// 创建一个 Thread 类的子类
public class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0;i < 20; i++){
            System.out.println("run: " + i);
        }
    }
}
