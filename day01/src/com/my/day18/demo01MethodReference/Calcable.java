package com.my.day18.demo01MethodReference;

@FunctionalInterface
public interface Calcable {
    // 定义一个抽象方法，传递一个整数，对整数进行去绝对值
    int calcAbs(int i);
}
