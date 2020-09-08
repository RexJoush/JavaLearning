package com.my.day07.demo01Mutil;

/*
    代码当中体现多态性，就是父类引用指向子类对象
    格式：
        父类名称 对象名 = new 子类名称 ()
        接口名称 对象名 = new 实现类名称 ()
 */
public class Demo01Multi {
    public static void main(String[] args) {

        // 父类的引用，指向子类的对象
        Fu obj = new Zi();
        obj.method();
        obj.methodFu();
    }
}
