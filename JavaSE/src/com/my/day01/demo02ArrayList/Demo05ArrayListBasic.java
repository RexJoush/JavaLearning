package com.my.day01.demo02ArrayList;

import java.util.ArrayList;

/*
    如果想要向 ArrayList 当中存放基本类型，需使用包装类。
    基本类型    包装类
    byte        Byte
    short       Short
    int         Integer
    long        Long
    float       Float
    double      Double
    char        Character
    boolean     Boolean

    从 jdk 1.5+ 开始，支持自动装箱，自动拆箱。
    自动装箱  基本类型 ---> 包装类型
    自动拆箱  包装类型 ---> 基本类型

 */



public class Demo05ArrayListBasic {
    public static void main(String[] args) {
        ArrayList<String> listA = new ArrayList<>();
        // 泛型只能是引用类型，不能是基本类型，错误
        // ArrayList<int> listB = new ArrayList<>();

        ArrayList<Integer> listC = new ArrayList<>();
        listC.add(100);
        listC.add(200);
        System.out.println(listC); // [100,200]

    }
}
