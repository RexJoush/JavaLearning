package com.joush.day08.demo02Generic;

/*
    测试含有泛型的接口
 */
public class Demo04GenericInterface {
    public static void main(String[] args) {

        // 创建 GenericInterfaceImpl1 对象，调用方法
        GenericInterfaceImpl1 gi1 = new GenericInterfaceImpl1();
        gi1.method("aaa"); // aaa

        // 创建 GenericInterfaceImpl2 对象
        GenericInterfaceImpl2<String> gi2 = new GenericInterfaceImpl2<>();
        gi2.method("aaa");
        GenericInterfaceImpl2<Double> gi3 = new GenericInterfaceImpl2<>();
        gi3.method(8.0);
    }
}
