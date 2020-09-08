package com.my.day17.demo03FunctionInterface;

/*
    函数式接口的使用，一般可作为方法的参数和返回值
 */
public class Demo01Test {
    // 定义一个方法，参数使用函数式接口
    public static void show(MyFunctionInterface myInter){
        myInter.method();
    }

    public static void main(String[] args) {
        // 调用 show 方法，方法参数是一个接口，所以可以传递接口的匿名内部类
        show(new MyFunctionInterface() {
            @Override
            public void method() {
                System.out.println("匿名内部类重写");
            }
        });

        // 调用 show 方法，使用 Lambda 表达式
        show(()->{
            System.out.println("Lambda 表达式重写");
        });

        // 简化 Lambda 表达式
        show(()-> System.out.println("Lambda 表达式重写"));

    }
}
