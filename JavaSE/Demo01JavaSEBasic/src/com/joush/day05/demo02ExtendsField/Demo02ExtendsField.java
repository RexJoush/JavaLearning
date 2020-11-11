package com.joush.day05.demo02ExtendsField;

/*
    在父子类的继承关系中，如果变量重名，则创建子类对象是访问有两种方式
    直接通过子类对象访问成员变量
        等号左边是谁，就优先用谁，没有就向上找
    间接通过成员方法访问成员变量
        看方法属于谁，属于谁就优先用谁
 */
public class Demo02ExtendsField {

    public static void main(String[] args) {
        Fu fu = new Fu();
        System.out.println(fu.fuNum); // 10

        Zi zi = new Zi();
        System.out.println(zi.fuNum); // 10
        System.out.println(zi.ziNum); // 20

        // zi 是用 Zi 定义的，所以访问子类 num
        System.out.println(zi.num); // 200

        // 这个方法是子类的，优先用子类
        zi.methodZi(); // 200
        // 在父类定义的，所以访问的是父类的 num
        zi.methodFu(); // 100

    }
}
