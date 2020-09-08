package com.my.day01.demo03ArrayListPrictise;

import java.util.ArrayList;

/*
    定义 Student 学生类，添加到集合，并遍历
 */
public class Demo02ArrayListStudent {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();

        Student one = new Student("a",20);
        Student two = new Student("b",30);
        Student three = new Student("c",40);
        Student four = new Student("d",50);

        list.add(one);
        list.add(two);
        list.add(three);
        list.add(four);

        // 遍历
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName() + "," + list.get(i).getAge());
        }

    }
}
