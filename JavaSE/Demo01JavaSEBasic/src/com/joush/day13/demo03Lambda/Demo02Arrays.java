package com.joush.day13.demo03Lambda;

import java.util.Arrays;
import java.util.Comparator;

/*
    Lambda 表达式有参数有返回值的练习
    需求
        使用数组存储多个 Person 对象，对数组中的 Person 对象常用 Arrays 的 sort 方法，通过年龄进行升序
 */
public class Demo02Arrays {
    public static void main(String[] args) {
        // 使用数组存储多个 Person 对象
        Person[] arr = {
                new Person("柳岩",38),
                new Person("迪丽热巴",18),
                new Person("古力娜扎",19)
        };

        // 对数组中的 Person 对象使用 Arrays 的 sort 方法通过年龄进行排序
        Arrays.sort(arr, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        });

        for (Person person : arr) {
            System.out.println(person);
        }

        // 使用 Lambda 表达式，简化匿名内部类
        Arrays.sort(arr,(o1, o2)-> o1.getAge() - o2.getAge());

    }
}
