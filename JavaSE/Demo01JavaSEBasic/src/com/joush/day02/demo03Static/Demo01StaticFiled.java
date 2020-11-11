package com.joush.day02.demo03Static;

/*
    如果一个变量使用了static关键字，那么这个变量不再属于对象自己，而属于整个类
 */
public class Demo01StaticFiled {
    public static void main(String[] args) {
        Student one = new Student("郭靖",19);
        Student two = new Student("黄蓉",16);
        one.room = "101教室";
        System.out.println("姓名:" + one.getName() + ",年龄：" + one.getAge() +
                ",教室：" + one.room + ",学号：" + one.getId());
        System.out.println("姓名:" + two.getName() + ",年龄：" + two.getAge() +
                ",教室：" + two.room + ",学号：" + two.getId());
    }
}
