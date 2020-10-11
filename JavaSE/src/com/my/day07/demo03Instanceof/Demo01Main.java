package com.my.day07.demo03Instanceof;

/*
    向上转型一定是安全的，正确的，一旦向上转型为父类，子类的特有方法则不能使用
    解决方法，使用向下转型，还原回去

    向下转型
    格式：
        子类名称 对象名 = （子类名称）父类对象;
    将父类对象还原成本来的子类对象

 */

public class Demo01Main {
    public static void main(String[] args) {

        // 对象的向上转型，父类引用指向子类对象
        Animal animal = new Cat();
        animal.eat();
        //animal.catchMouse(); //错误写法

        // 对象的向下转型，必须保证创建时就是猫，才能还原称为猫
        Cat cat = (Cat) animal;
        cat.CatchMouse();

        /*
            错误的向下转型
            错误写法，编译不出错，但运行抛异常
            java.lang.ClassCastException
         */
        Dog dog = (Dog) animal;

    }
}
