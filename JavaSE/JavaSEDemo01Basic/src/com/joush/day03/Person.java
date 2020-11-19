package com.joush.day03;

import java.util.Objects;

public class Person {
    private String name;
    private int age;
    public Person(){

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    /*
        Object 类的 equals 方法，默认比较两个对象的地址值，没有意义
        重写equals方法，比较对象的属性

        问题：隐含一个多态
            多态的弊端，无法使用子类特有的属性和方法
            Object obj = p2 = new Person("古力娜扎",18);
        解决：
             使用强制转换将 obj 转为 Person
     */

//    @Override
//    public boolean equals(Object obj) {
//
//        // 如果传递的是 this 本身，返回 true
//        if (obj == this)
//            return true;
//
//        // 如果接收为 null，直接返回 false
//        if (obj == null)
//            return false;
//
//        // 增加判断，防止类型强转异常
//        if (obj instanceof Person){
//            // 将 obj 转为 Person
//            Person p = (Person) obj;
//            return this.name.equals(p.name) && this.age == p.age;
//        }
//        return false;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        // getClass() != o.getClass() 利用反射技术判断 o 是否是 Person 类型

        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
