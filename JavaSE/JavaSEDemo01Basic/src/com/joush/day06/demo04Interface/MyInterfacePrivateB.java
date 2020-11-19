package com.joush.day06.demo04Interface;


public interface MyInterfacePrivateB {

    public static void methodDefault1(){
        System.out.println("默认方法1");
        methodStaticCommon();
    }
    public static void methodDefault2(){
        System.out.println("默认方法2");
        methodStaticCommon();
    }

    // jdk9 允许定义私有
    private static void methodStaticCommon(){
        System.out.println("AAA");
        System.out.println("BBB");
        System.out.println("CCC");
    }
}
