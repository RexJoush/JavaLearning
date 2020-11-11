package com.joush.day17.demo05FunctionExample;

import java.util.function.Consumer;

/*
    java.util.function.Consumer<T> 接口则正好与 Supplier 相反
        它不是生产一个数据，而是消费一个数据，其数据类型由泛型决定
    Consumer 接口中包含抽象方法 accept(T t); 意为消费一个指定泛型的数据
    如何消费，需要自定义
 */
public class Demo03Consumer {

    /*
        定义一个方法
            参数传递一个字符串的姓名
            传递 Consumer 接口，泛型使用 String
            可以使用 Consumer 接口消费字符串的姓名
     */

    public static void method(String name, Consumer<String> consumer){
        consumer.accept(name);
    }

    public static void main(String[] args) {
        // 调用 method 方法，传递字符串姓名，方法的另一个参数是 Consumer 接口
        method("赵丽颖",(name)-> System.out.println(name));
    }

    //


}
