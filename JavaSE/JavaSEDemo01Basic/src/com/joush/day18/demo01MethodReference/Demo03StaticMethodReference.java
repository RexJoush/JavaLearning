package com.joush.day18.demo01MethodReference;

/*
    通过类名引用静态成员方法
    适用前提
        1.类已经存在
        2.静态成员方法也已经存在
 */
public class Demo03StaticMethodReference {
    // 定义一个方法，方法传递要计算绝对值的整数，和函数式接口 Calcable
    public static int method(int number, Calcable c){
        return c.calcAbs(number);
    }

    public static void main(String[] args) {
        // 调用 method 方法，传递计算绝对值的整数，和 Lambda 表达式
        method(-10,(n)->{
            // 计算绝对值并返回
            return Math.abs(n);
        });

        /*
        使用方法引用优化 Lambda 表达式
            1.Math 类存在
            2.静态方法 Abs 存在
        */
        int number2 = method(-10,Math::abs);
        System.out.println(number2);
    }

}
