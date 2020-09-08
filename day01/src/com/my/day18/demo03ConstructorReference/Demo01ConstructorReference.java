package com.my.day18.demo03ConstructorReference;

public class Demo01ConstructorReference {

    // 定义一个方法，参数传递姓名和 PersonBuilder 接口

    public static void printName(String name, PersonBuilder pb){
        Person person = new Person(name);
        System.out.println(person.getName());
    }

    public static void main(String[] args) {
        // 调用 printName 方法
        printName("张三",name -> new Person(name));


        /*
            使用方法引用优化 Lambda 表达式
            构造方法 new Person(String name) 已知
            创建对象 new 已知
            可以使用 Person 引用创建对象
         */
        printName("古力娜扎",Person::new);

    }

}

