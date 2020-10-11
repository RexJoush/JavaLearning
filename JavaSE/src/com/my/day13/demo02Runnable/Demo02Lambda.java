package com.my.day13.demo02Runnable;

/*
    Lambda 表达式的标准格式
        1.一些参数
        2.一个箭头
        3.一段代码
    格式
        (参数列表) -> {
            // 代码
        }
 */
public class Demo02Lambda {
    public static void main(String[] args) {
        // 使用匿名内部类的方法，实现多线程
        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "新的线程创建");
            }
        }).start();

        // 使用 Lambda 表达式
        new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "新的线程创建");
        }).start();
    }
}
