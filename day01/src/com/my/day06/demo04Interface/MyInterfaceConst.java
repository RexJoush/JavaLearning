package com.my.day06.demo04Interface;

/*
    接口中可以定义成员变量，但必须使用 public static final 三个关键字修饰
    格式
        public static final 数据类型 常量名称 = 数据值;
    注意：
        一旦使用 final 修饰，则变量不可变
    1.接口当中的常量可以省略 public static final, 但默认已经有了
    2.接口中的常量必须初始化，否则报错
    3.接口中常量的名称用全大写，并用下划线进行分割，（推荐规则）
 */
public class MyInterfaceConst {

    // 这其实是一个常量，一旦赋值，不可修改
    public static final int NUM_OF_MY_CLASS = 10;

}
