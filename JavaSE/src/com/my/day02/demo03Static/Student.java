package com.my.day02.demo03Static;

public class Student {
    private int id;
    private String name; // 姓名
    private int age; // 年龄
    private static int idCounter = 0; //学号计数器，每当 new 一个学生就 +1
    static String room; // 所在教室


    public Student(){
        this.id = ++idCounter;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.id = ++idCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
