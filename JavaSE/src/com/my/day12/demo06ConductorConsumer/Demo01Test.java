package com.my.day12.demo06ConductorConsumer;

public class Demo01Test {
    public static void main(String[] args) {
        BaoZi bz = new BaoZi();
        // 创建生产者线程
        new Conductor(bz).start();

        // 创建消费者线程
        new Consumer(bz).start();
    }
}
