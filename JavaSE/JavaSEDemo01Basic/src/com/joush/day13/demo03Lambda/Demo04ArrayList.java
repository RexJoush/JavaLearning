package com.joush.day13.demo03Lambda;

import java.util.ArrayList;

/*
    Lambda 表达式可推导，可省略的
    凡事根据上下文推导出来的内容，都可以省略
        1.() 参数列表的数据类型可以省略
        2.() 参数如果只有一个，那么类型和 ()都可以省略
        3.如果 {} 中的代码只有一行，无论是否有返回值，都可以省略 {}和 return ;
            注意，{} return ; 三个必须一起省略

    1.使用 lambda 表达式必须有接口，且接口内有且仅有一个抽象方法
    2.使用 Lambda 表达式必须具有上下文推断，方法参数或局部变量必须类型为 Lambda 对应的接口类型，才能使用
        有且只有一个抽象方法的接口，称为 函数式接口
 */
public class Demo04ArrayList {

    public static void main(String[] args) {
        // jdk1.7 之前，集合泛型前后都要写
        ArrayList<String> list = new ArrayList<String>();

        // jdk1.7 之后，后面泛型可以不写
        ArrayList<String> list2 = new ArrayList<>();

        // 使用 Lambda 表达式
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "新的线程创建");
        }).start();

        // 优化 Lambda 表达式
        new Thread(()->System.out.println(Thread.currentThread().getName() + "新的线程创建")).start();
    }

}
