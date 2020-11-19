package com.joush.day12.demo06ConductorConsumer;

/*
    吃货类
 */
public class Consumer extends Thread{

    private BaoZi bz;
    public Consumer(BaoZi bz){
        this.bz = bz;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (bz){
                if (!bz.flag){ // 没有包子，等待
                    try {
                        bz.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 被唤醒之后执行吃包子
                System.out.println("正在吃" + bz.getPi() + bz.getXian());
                bz.flag = false;
                bz.notify();
                System.out.println("吃完： " + bz.getPi() + bz.getXian());
                System.out.println("===========");
            }
        }

    }
}
