package com.joush.day07.demo06InnerClass;

/*
    一个类包含另一个类
    1.成员内部类
    2.局部内部类（包含匿名内部类）

    成员内部类的定义格式
    修饰符 class 类名称 {
        修饰符 class 类名称 {
            // ...
        }
        // ...
    }
    注意，内用外随意，外用内一定借助内部类对象
    ====================

    如何使用成内部类
    1.间接方式，在外部类的方法中，使用内部类，然后 main 只是调用外部类的办法
    2.直接方式，
    外部类.内部类 对象名 = new 外部类名称().内部类名称();

 */
public class Demo01InnerClass {
    public static void main(String[] args) {
        Body body = new Body(); // 外部类的对象

        // 间接方式，通过调用外部类的方法，访问内部类
        body.methodBody();

        // 直接方式，直接创建内部类的对象
        Body.Heart heart = new Body().new Heart();
        heart.beat();
    }
}
