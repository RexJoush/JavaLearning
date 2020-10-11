package com.my.day02.demo03Static;

public class Person {
    static {
        System.out.println("静态代码块执行！");
    }
    Person(){
        System.out.println("构造方法执行");
    }
}
