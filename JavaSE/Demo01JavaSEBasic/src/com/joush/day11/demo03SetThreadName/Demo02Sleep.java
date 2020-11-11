package com.joush.day11.demo03SetThreadName;

/*
    public static void sleep(long millis); 使当前执行的线程以指定的毫秒数暂停
    毫秒数结束之后程序继续执行
 */
public class Demo02Sleep {
    public static void main(String[] args) {
        // 模拟秒表

        for (int i = 0; i < 60; i++) {
            System.out.println(i);

            // 使用 Thread 类的 sleep 方法
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
