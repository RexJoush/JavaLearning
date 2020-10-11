package com.my.day02.demo01String;

/*
    java.lang.String 类代表字符串
    字符串的特点：
    1.字符串的内容永不可变。
    2.正是因为字符串不可改变，所以字符串可以共享使用
    3.字符串效果上相当于 char[] 型字符数组，底层原理是 byte[] 字节数组

    创建字符串的常见 3+1 种方式
    三种构造方法
    1.public String(); 创建一个空白字符串
    2.public String(char[] array) 根据字符数组的内容，创建字符串
    3.public String(byte[] array) 根据字节数组的内容，创建字符串
    一种直接创建
    String str = "Hello";
 */

public class Demo01String {
    public static void main(String[] args) {
        // 空白参数
        String str1 = new String();
        System.out.println("first string:" + str1);

        // 字符数组创建
        char[] charArray = {'a','b','c'};
        String str2 = new String(charArray);
        System.out.println("second string:" + str2);

        // 字节数组创建字符串
        byte[] byteArray = {97,98,99};
        String str3 = new String(byteArray);
        System.out.println("third string:" + str3);

        // 直接创建
        String str4 = "abc";
        System.out.println(str4);

    }
}
