package com.my.day05.demo06;

public class Zi extends Fu {
    int num = 20;

    public Zi(){
        this(123); // 本类的无参构造，调用本类的有参构造
        // 此处 this(xx) 必须是第一个语句，和 super() 一样
        // 并且 this(xx) 和 super() 只能使用一个
    }

    public Zi(int n){

    }

    public void show(){
        int num = 10;
        System.out.println(num); // 10
        System.out.println(this.num); // 20
        System.out.println(super.num); //30
    }
}
