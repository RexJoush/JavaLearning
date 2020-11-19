package com.joush.day06.demo01Abstract;

/*
    抽象方法，加上 abstract 关键字，去掉大括号，直接 ; 结束
    抽象方法所在的类必须是抽象类

    如何使用抽象类和抽象方法
    1.不能直接 new 抽象类的对象
    2.必须用子类继承抽象父类
    3.子类必须重写父类的所有抽象方法
        去掉抽象方法的 abstract 关键字，把方法体实现
    4.创建子类对象进行使用
 */
public abstract class Animal {

    // 这是一个抽象方法
    public abstract void eat();

    public void normalMethod(){

    }
}
