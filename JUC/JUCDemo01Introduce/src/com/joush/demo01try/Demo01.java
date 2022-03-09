package com.joush.demo01try;

/**
 * @author Rex Joush
 * @time 2022.03.08
 */
public class Demo01 {

    public static void main(String[] args) {
        synchronized (new Demo01()) {
            Thread aa = new Thread(() -> {
                // 打印当前线程是否是守护线程
                System.out.println(Thread.currentThread().getName() + " :" + Thread.currentThread().isDaemon());
                while (true) {

                }
            }, "aa");
            // 设置当前线程为守护线程
            aa.setDaemon(true);
            aa.start();
        }

        System.out.println(Thread.currentThread().getName() + " over");
    }
}
