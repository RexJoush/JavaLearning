package com.joush.day06.demo03AbstractClass;

public class Demo01Main {
//    Animal animal = new Animal(); 错误
//    Dog dog = new Dog(); 错误，也是抽象类

    public static void main(String[] args) {
        Dog2Ha dog2Ha = new Dog2Ha();
        dog2Ha.eat(); // 狗吃骨头
        dog2Ha.sleep(); // 呼呼呼！

        DogGolden dogGolden = new DogGolden();
        dogGolden.eat(); // 狗吃骨头
        dogGolden.sleep(); // 嘿嘿嘿！

    }


}
