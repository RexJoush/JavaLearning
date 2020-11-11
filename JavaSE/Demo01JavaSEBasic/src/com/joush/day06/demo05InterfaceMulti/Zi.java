package com.joush.day06.demo05InterfaceMulti;

/*
    优先继承，其次接口
 */
public class Zi extends Fu implements MyInterface {
    public static void main(String[] args) {
        Zi zi = new Zi();
        zi.method(); // 父类方法
    }
}
