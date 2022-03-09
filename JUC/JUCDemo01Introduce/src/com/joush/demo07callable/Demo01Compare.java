package com.joush.demo07callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */
// 比较两个接口 Runnable 和 Callable

// 实现 runnable
class MyThread1 implements Runnable {

    @Override
    public void run() {

    }
}
// 实现 callable
class MyThread2 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 200;
    }
}

public class Demo01Compare {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Runnable 接口创建方法
        new Thread(new MyThread1(), "AA").start();

        // Callable 线程创建方法

        // FutureTask的使用
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
        // lambda 表达式实现
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " come in callable");
            return 1024;
        });

        new Thread(futureTask2, "lucy").start();

        // 判断任务 2 是否完成
        while (!futureTask2.isDone()) {
            System.out.println("wait...");
        }

        // 调用FutureTask 的 get 方法，获取结果
        System.out.println(futureTask2.get());

        System.out.println(Thread.currentThread().getName() + " over");
    }
}
