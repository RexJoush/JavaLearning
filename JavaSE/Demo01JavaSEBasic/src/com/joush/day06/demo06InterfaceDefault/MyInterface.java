package com.joush.day06.demo06InterfaceDefault;

/*
   这个接口有四个方法
   methodA 来源于接口 A
   methodB 来源于接口 B
   methodCommon 同时来源于 A 和 B
   method 自己
 */
public interface MyInterface extends MyInterfaceA,MyInterfaceB{

    public abstract void method();

    @Override
    default void methodDefault() {

    }
}
