package com.joush.demo13threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 创建线程池
public class Demo01CreatePool {

    public static void main(String[] args) {
        // 创建一池 N 线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

        // 创建一池一线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        // 创建一池可扩容线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        // 10 个顾客，模拟 5 个窗口进行处理
        try {
            for (int i = 1; i <= 10; i++) {
                threadPool3.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 结束当前任务，归还线程
            threadPool3.shutdown();
        }

    }

}
