package com.joush.demo13threadpool;

import java.util.concurrent.*;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 自定义线程池
public class Demo02CrdThreadPool {

    public static void main(String[] args) {
        // 自定义线程池
        ExecutorService threadPool = new ThreadPoolExecutor(
                2, // 常驻线程数两
                5, // 最大线程数量
                2L, // long 类型，最大存活时间
                TimeUnit.SECONDS, // 最大存活时间的单位，秒
                new ArrayBlockingQueue<>(3), // 阻塞队列
                Executors.defaultThreadFactory(), // 创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
        );

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 结束当前任务，归还线程
            threadPool.shutdown();
        }
    }

}
