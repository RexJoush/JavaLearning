package com.my.day03;
/*
    Person 类默认继承了 Object 类，所以可以用Object类的 equals 方法

    public boolean equals (Object obj){
        return (this == obj);
    }

    Object obj, 可以传递任何对象， == 返回一个布尔值
    基本数据类型：比较值
    引用数据类型：比较地址值

 */
public class Demo03Equals {
    public static void main(String[] args) {
        Person p1 = new Person("迪丽热巴",19);
        Person p2 = new Person("迪丽热巴",19);
        System.out.println("p1: " + p1); // p1: com.my.day03.Person@1b6d3586
        System.out.println("p2: " + p2); // p2: com.my.day03.Person@4554617c
        //p1 = p2; // 把 p2 地址赋给 p1

        System.out.println(p1.equals(p2));

    }
}
