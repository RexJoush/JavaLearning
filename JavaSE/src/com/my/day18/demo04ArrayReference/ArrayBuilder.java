package com.my.day18.demo04ArrayReference;

/*
    定义一个创建数组的函数式接口
 */
@FunctionalInterface
public interface ArrayBuilder {
    // 定义一个创建 int 型数组的函数型接口
    public int[] getIntArray(int arrayLength);
}
