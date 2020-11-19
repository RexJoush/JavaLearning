package com.joush.day02.demo03Static;

public class MyClass {
    int num; // 成员变量
    static int numStatic; //静态变量

    // 成员方法
    public void method(){

        // 成员方法可以访问变量，和静态变量
        System.out.println(num);
        System.out.println(numStatic);
        System.out.println("这是一个成员方法！");
    }

    // 静态方法
    public static void methodStatic(){
        System.out.println("这是一个静态方法！");
    }
}
