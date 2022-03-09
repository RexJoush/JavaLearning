package com.joush.demo12blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Rex Joush
 * @time 2022.03.09
 */

// 阻塞队列演示
public class Demo01BlockingQueue {

    //
    public static void main(String[] args) {
        // 创建阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        // 第一组
        // 添加数据
        for (int i = 0; i < 4; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    blockingQueue.put(String.valueOf(num));
                    System.out.println(blockingQueue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
