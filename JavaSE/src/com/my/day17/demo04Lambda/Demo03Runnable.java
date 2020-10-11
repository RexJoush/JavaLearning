package com.my.day17.demo04Lambda;


/*
    java.lang.Runnable 就是一个函数式接口，
    有一个 startThread 方法使用该接口作为参数，那么就可以使用 Lambda 进行传参
 */
public class Demo03Runnable {
    // 定义一个方法，startThread，参数使用函数式接口

    public static void startThread(Runnable run){
        new Thread(run).start();
    }

    public static void main(String[] args) {

        // 调用 startThread 方法，参数是一个接口，可以传递接口的匿名内部类
        startThread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " has been started");
            }
        });

        // 调用 startThread 方法，参数是一个接口，可以传递 Lambda 表达式
        startThread(()-> System.out.println(Thread.currentThread().getName() + " has been started!"));
    }

}
