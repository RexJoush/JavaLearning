package com.joush.day17.demo05FunctionExample;

import java.util.function.Supplier;

/*
    常用函数式接口
    java.util.Supplier<T> 接口仅包含一个无参的方法 T get(); 用来获取一个泛型参数指定的对象数据

    Supplier<T> 接口被称为生产型接口，指定接口泛型是什么数据，就可以产生什么数据

 */
public class Demo01Supplier {

    // 定义一个方法，参数传递 Supplier<T> 接口，泛型执行 String get() 返回一个 String
    public static String getString(Supplier<String> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        // 调用 getString 方法，方法的参数 Supplier<T> 是一个函数式接口，可以使用 Lambda 表达式

        String string1 = getString(() -> {
            // 生产一个字符串并返回
            return "aaa";
        });

        System.out.println(string1); // aaa

        // 优化 Lambda
        String string2 = getString(() -> "aaa");
        System.out.println(string2); // aaa
    }

}
