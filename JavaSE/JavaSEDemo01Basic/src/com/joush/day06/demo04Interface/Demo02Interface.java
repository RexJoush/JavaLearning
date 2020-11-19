package com.joush.day06.demo04Interface;

/*
    1.接口的默认方法，可以通过接口实现类的对象直接调用
    2.接口的默认方法，可以被接口实现类重写
 */
public class Demo02Interface {
    public static void main(String[] args) {
        MyInterfaceDefaultA a = new MyInterfaceDefaultA();
        a.methodAbs(); // 调用抽象方法，实际运行的是右侧的实现类

        // 调用默认方法，实现类当中没有的话，会向上找接口
        a.methodDefault();

        System.out.println("==========");
        MyInterfaceDefaultB b = new MyInterfaceDefaultB();
        b.methodAbs();
        b.methodDefault();

    }
}
