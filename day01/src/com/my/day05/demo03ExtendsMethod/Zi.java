package com.my.day05.demo03ExtendsMethod;

public class Zi extends Fu{
    public void methodZi(){
        System.out.println("子类方法执行");
    }

    @Override
    public void method(){
        System.out.println("子类重名方法执行");
    }
}
