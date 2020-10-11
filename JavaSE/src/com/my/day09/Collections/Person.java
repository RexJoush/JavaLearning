package com.my.day09.Collections;

public class Person implements Comparable<Person>{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // 重写排序的规则
    @Override
    public int compareTo(Person o) {
        // 自定义比较两个人的年龄（this，参数）
        // 年龄升序排序
        return this.getAge() - o.getAge();
    }

}
