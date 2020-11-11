package com.joush.day07.demo02MultiMethod;

public class Zi extends Fu {
    @Override
    public void method() {
        System.out.println("子类方法");
    }

    public void methodZi(){
        System.out.println("子类特有方法");
    }
}
