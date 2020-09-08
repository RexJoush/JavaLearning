package com.my.day02.demo03Static;

public class Demo03StaticStudent {
    public static void main(String[] args) {
        // 首先设置一下教室，这是静态的，用通过类名来调用
        Student.room = "101教室";

        Student one = new Student("郭靖",19);
        System.out.println(one.getName());
        System.out.println(one.getAge());
        System.out.println(Student.room);
        System.out.println();
        Student two = new Student("黄蓉",16);
        System.out.println(two.getName());
        System.out.println(two.getAge());
        System.out.println(Student.room);
    }
}
