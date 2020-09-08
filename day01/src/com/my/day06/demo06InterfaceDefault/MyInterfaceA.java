package com.my.day06.demo06InterfaceDefault;

public interface MyInterfaceA {

    public abstract void methodA();
    public abstract void methodCommon();
    public default void methodDefault(){
        System.out.println("AAA");
    }

}
