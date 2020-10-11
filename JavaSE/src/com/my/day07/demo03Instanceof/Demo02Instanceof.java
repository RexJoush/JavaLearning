package com.my.day07.demo03Instanceof;

/*
    如何知道一个父类引用对象，本来是什么子类
    格式：
        对象 instanceof 类型
        这将会得到一个 boolean 值结果，将会判断前面是否能当做后面的实例
 */
public class Demo02Instanceof {

    public static void main(String[] args) {
        Animal animal = new Cat();

        // 判断 animal 是否是狗
        if (animal instanceof Dog){
            Dog dog = (Dog) animal;
        }

        // 判断 animal 是否是狗
        if (animal instanceof Cat){
            Cat cat = (Cat) animal;
        }

        giveMeAPet(new Dog());
    }

    public static void giveMeAPet(Animal animal){
        if (animal instanceof Dog){
            Dog dog = (Dog) animal;
        }

        if (animal instanceof Cat){
            Cat cat = (Cat) animal;
        }
    }
}
