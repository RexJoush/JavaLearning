package com.my.day06.demo04Interface;

public class Demo04Interface {
    public static void main(String[] args) {
        MyInterfacePrivateB.methodDefault1();
        MyInterfacePrivateB.methodDefault2();

        // 私有无法访问
        //MyInterfacePrivateB.methodStaticCommon();
    }
}
