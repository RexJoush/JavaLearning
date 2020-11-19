package com.joush.day18.demo08ReflectAnnotation;

public class Calculator {

    // 加法
    @Check
    public void add(){
        int a = 2/0;
        System.out.println("1 + 0 = " + (1 + 0));
    }
    // 减法
    @Check
    public void sub(){
        System.out.println("1 - 0 = " + (1 - 0));
    }
    // 乘法
    @Check
    public void mul(){
        System.out.println("1 * 0 = " + (1 * 0));
    }
    // 除法
    @Check
    public void div(){
        System.out.println("1 / 0 = " + (1 / 0));
    }

    public void show(){
        System.out.println("show!!!");
    }

}
