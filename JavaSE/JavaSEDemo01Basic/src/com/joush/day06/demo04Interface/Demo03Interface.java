package com.joush.day06.demo04Interface;

/*
    注意：
        不能通过接口的实现类对象来调用接口当中的静态方法
    正确用法，通过接口名称直接调用即可
 */
public class Demo03Interface {
    public static void main(String[] args) {
        // 创建实现类的对象
        MyInterfaceStaticImpl impl = new MyInterfaceStaticImpl();


        // 错误写法
        // impl.methodStatic();

        // 接口名称直接调用
        MyInterfaceStatic.methodStatic();
    }
}
