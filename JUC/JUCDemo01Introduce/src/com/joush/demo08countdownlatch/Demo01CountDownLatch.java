package com.joush.demo08countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 演示 CountDownLatch
public class Demo01CountDownLatch {

    // 6 个同学离开教室之后，班长锁门
    public static void main(String[] args) throws InterruptedException {

        // 创建 CountDownLatch 对象
        CountDownLatch count = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 号同学离开了！");
                // 初始值减一
                count.countDown();
            }, String.valueOf(i)).start();
        }
        // 当count 计数器变为 0 后，await 才会放行
        count.await();
        System.out.println(Thread.currentThread().getName() + " 班长锁门");

    }

}
