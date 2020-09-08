package com.my.day12.demo06ConductorConsumer;

/*
    注意事项
        包子铺线程和包子线程，通信关系，也叫互斥关系，使用同步技术，保证两个线程只有一个在执行
        锁对象必须保证唯一，可以用包子作为锁对象，需要把包子对象作为参数传递
            1.在成员位置创建一个包子变量
            2.使用带参构造，为包子变量赋值
 */
public class Conductor extends Thread {
    // 包子
    private BaoZi bz;

    Conductor(BaoZi bz){
        this.bz = bz;
    }

    @Override
    public void run() {
        int count = 0; // 两种包子
        while (true) {  // 一直生产包子
            // 使用同步技术
            synchronized (bz){
                if (bz.flag){  // 有包子

                    try {
                        bz.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 没包子
                if (count % 2 == 0){
                    // 生产薄皮三鲜包子
                    bz.setPi("薄皮");
                    bz.setXian("三鲜");
                } else {
                    // 生产冰皮牛肉大葱包子
                    bz.setPi("冰皮");
                    bz.setXian("牛肉大葱");
                }
                count++;
                System.out.println("包子正在生产： " + bz.getPi() + bz.getXian());
                // 生产包子需要 3s
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 修改包子状态
                bz.flag = true;
                // 唤醒吃货线程
                bz.notify();
                System.out.println("生产成功： " + bz.getPi() + bz.getXian());
            }
        }
    }
}
