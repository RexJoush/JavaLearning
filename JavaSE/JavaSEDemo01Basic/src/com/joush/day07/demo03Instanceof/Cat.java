package com.joush.day07.demo03Instanceof;

public class Cat extends Animal {
    @Override
    public void eat() {
        System.out.println("猫吃鱼");
    }

    // 子类特有方法
    public void CatchMouse(){
        System.out.println("猫捉老鼠");
    }

}
