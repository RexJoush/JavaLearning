package com.my.day06.demo05InterfaceMulti;

public interface MyInterface {
    public default void method(){
        System.out.println("接口方法");
    }
}
