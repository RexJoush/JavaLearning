package com.joush.day07.demo06InnerClass;

public class Demo02InnerClass {
    public static void main(String[] args) {
        Outer.Inner obj = new Outer().new Inner();
        obj.methodInner();
    }
}
