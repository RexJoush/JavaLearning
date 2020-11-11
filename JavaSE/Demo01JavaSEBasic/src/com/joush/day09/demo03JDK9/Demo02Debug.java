package com.joush.day09.demo03JDK9;

/*
    Debug 可以让代码逐行执行，查看代码的执行过程，调试程序中出现的 bug
    使用方式
        在行号右边添加断点，一般添加在方法的第一行
        F8 逐行执行程序
        F7 进入方法
        Shift + F8 跳出方法
        F9 跳到下一个断点，如果没有，那么就程序结束
        Ctrl + F2 退出 Debug，停止程序
 */
public class Demo02Debug {
    public static void main(String[] args) {
        /*int a = 10;
        int b = 20;
        int sum = a + b;
        System.out.println(sum);*/

        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }

        print();

    }

    private static void print() {
        System.out.println("HelloWorld");
        System.out.println("HelloWorld");
        System.out.println("HelloWorld");
        System.out.println("HelloWorld");
        System.out.println("HelloWorld");
    }


}
