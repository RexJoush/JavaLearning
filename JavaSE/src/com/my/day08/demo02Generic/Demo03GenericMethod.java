package com.my.day08.demo02Generic;

/*
    测试含有泛型的方法
 */
public class Demo03GenericMethod {
    public static void main(String[] args) {
        GenericMethod gm = new GenericMethod();
        gm.method01("AAA"); // AAA
        gm.method01(8.8); // 8.8
        gm.method01(true); // true

        gm.method02(1); // 1,但不建议创建对象使用
        GenericMethod.method02("aaa"); // aaa

    }
}
