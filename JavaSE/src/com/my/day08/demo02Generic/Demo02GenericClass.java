package com.my.day08.demo02Generic;

public class Demo02GenericClass {
    public static void main(String[] args) {
        /*
        不写泛型，默认 Object
        GenericClass gc = new GenericClass();
        gc.setName("只能是字符串");
        System.out.println(gc.getName()); // 只能是字符串
        */

        // 泛型使用 Integer
        GenericClass<Integer> gc2 = new GenericClass<>();
        gc2.setName(111);
        System.out.println(gc2.getName()); // 111

        // 泛型使用 String
        GenericClass<String> gc3 = new GenericClass<>();
        gc3.setName("小明");
        System.out.println(gc3.getName()); // 小明

    }
}
