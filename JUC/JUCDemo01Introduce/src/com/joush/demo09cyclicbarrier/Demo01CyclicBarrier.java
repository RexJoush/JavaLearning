package com.joush.demo09cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 集齐七颗龙珠可以召唤神龙
public class Demo01CyclicBarrier {

    // 创建固定值
    private static final int number = 7;

    public static void main(String[] args) {
        // 创建 CyclicBarrier 对象，设定固定值，并写出完成后实现什么功能
        CyclicBarrier cyclicBarrier = new CyclicBarrier(number, () -> {
            System.out.println("七颗龙珠，召唤神龙！！！");
        });

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "星球");
                    // 等待
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
        cyclicBarrier.reset();
    }

}
