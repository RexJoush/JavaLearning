package com.joush.day11.demo04Runnable;

/*
    匿名内部类实现线程创建
    匿名：没有名字
    内部类：写在其他类的内部

    匿名内部类的作用，简化代码
        把子类继承父类，重写父类方法，创建子类对象合成一步完成
        把实现类实现接口重写接口中的方法，创建实现类对象合成一步
    匿名内部类的最终产物，子类、实现类的对象

    格式：
        new 父类/接口() {
            重写父类的或接口的方法
        }
 */
public class Demo02InnerClassThread {
    public static void main(String[] args) {
        // 线程的父类是 Thread
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }
            }
        }.start();

        // 线程的接口 Runnable
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + "程序员" + i);
                }
            }
        };
        new Thread(runnable).start();

        // 简化接口的方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(Thread.currentThread().getName() + "哈哈" + i);
                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      }
        }).start();
    }
}
