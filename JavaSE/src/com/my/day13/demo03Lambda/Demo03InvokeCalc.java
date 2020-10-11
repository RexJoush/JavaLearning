package com.my.day13.demo03Lambda;


public class Demo03InvokeCalc {
    public static void main(String[] args) {

        // 使用匿名内部类
        invokeCalc(2, 3, new Calculator() {
            @Override
            public int Calc(int a, int b) {
                return a+b;
            }
        });

        // 使用 lambda 表达式
        invokeCalc(20,30,(a,b) -> a + b);
    }

    // 定义一个方法
    private static void invokeCalc(int a, int b ,Calculator calculator){
        System.out.println(calculator.Calc(a, b));
    }

}
