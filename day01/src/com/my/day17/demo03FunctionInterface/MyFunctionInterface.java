package com.my.day17.demo03FunctionInterface;

/*
    函数式接口，有且只有一个抽象方法的接口，称之为函数式接口，当然接口中可以有其他方法，默认，静态，私有等
 */

// 注解，检测接口是否是函数式接口，即有且仅有一个抽象方法
@FunctionalInterface
public interface MyFunctionInterface {
    // 定义一个抽象方法
    public abstract void method();

}
