package com.joush.day08.demo02Generic;

/*
    定义一个含有泛型的类，模拟 ArrayList 集合，不确定使用什么类型的时候，可以使用泛型
 */
public class GenericClass<E> {

    private E name;

    public E getName() {
        return name;
    }

    public void setName(E name) {
        this.name = name;
    }
}
