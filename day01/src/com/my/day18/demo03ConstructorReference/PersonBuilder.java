package com.my.day18.demo03ConstructorReference;

/*
    定义一个创建 Person 对象的函数式接口
 */
@FunctionalInterface
public interface PersonBuilder {
    // 定义一个方法，根据姓名创建 Person 对象，并返回
    Person builderPerson(String name);
}
