package com.joush.case1.proxy;

public class Lenovo implements SaleComputer{
    @Override
    public String sale(double money) {
        System.out.println("花了" + money + "买了一台联想电脑");
        return "小新14pro";
    }

    @Override
    public void show() {
        System.out.println("展示电脑");
    }
}
