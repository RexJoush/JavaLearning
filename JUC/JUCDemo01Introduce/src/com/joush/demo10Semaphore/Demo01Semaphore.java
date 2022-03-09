package com.joush.demo10Semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 信号量，6 辆汽车，停三个车位
public class Demo01Semaphore {

    public static void main(String[] args) {
        // 创建 Semaphore，并初始化 3 个信号量
        Semaphore semaphore = new Semaphore(3);

        // 模拟六辆汽车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                // 抢占车位
                try {
                    // 抢到车位之后
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到了车位");
                    // 停车了随机的时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    System.out.println(Thread.currentThread().getName() + " 离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放车位
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
