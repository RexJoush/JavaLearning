package com.joush.day05.demo02ExtendsField;

public class Zi extends Fu{
    int ziNum = 20;
    int num = 200;

    public void methodZi(){
        // 使用本类的 num，本类有，优先使用
        System.out.println(num);
    }

}
