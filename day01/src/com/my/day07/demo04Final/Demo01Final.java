package com.my.day07.demo04Final;

/*
    final 关键字，代表最终的，不可改变的，可以修饰
        - 类
        - 方法
        - 局部变量
        - 成员变量
 */
public class Demo01Final {
    public static void main(String[] args) {
        int num1 = 10;
        System.out.println(num1); // 10
        num1 = 20;
        System.out.println(num1); // 20

        // 一旦使用 final 修饰变量，那么变量就不可更改，一次赋值，终生不变
        final int num2 = 200;
        System.out.println(num2);
        //num2 = 250; 错误写法

        // 正确写法
        final int num3;
        num3 = 20;

        // 对于基本类型来说，不可变指的是变量当中的数据不可变
        // 对于引用类型来说，不可变指的是变量的地址值不可变
        Student student1 = new Student("赵丽颖");
        System.out.println(student1.getName()); // 赵丽颖
        student1 = new Student("霍建华");
        System.out.println(student1);
        System.out.println(student1.getName()); // 霍建华

        final Student student2 = new Student("高圆圆");
        // 错误写法，final 不能重新改变地址，但可以改变内容
        // student2 = new Student("赵又廷");
        student2.setName("赵又廷");
        System.out.println(student2.getName()); // 赵又廷
    }
}
