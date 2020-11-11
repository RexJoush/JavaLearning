package com.joush.day02.demo02StringMethod;

/*
    String 中与获取相关的常用方法

    1.public int length() 获取字符串中的字符数，拿到长度
    2.public String concat() 将当前字符串和参数字符串拼接返回新的字符串
    3.public char charAt(int index) 获取指定索引位置的单个字符，从0开始
    4.public int indexOf(String str) 查找参数字符串出现在本字符串中首次出现的位置，如果没有，就返回 -1

 */
public class Demo02StringGet {
    public static void main(String[] args) {
        // 查找长度
        int length = "jsadhgfhasgfhagsf".length();
        System.out.println("string length is " + length);

        // 拼接字符串
        String str1 = "Hello";
        String str2 = "World";
        String str3 = str1.concat(str2);
        System.out.println(str1); // Hello,不变
        System.out.println(str2); // World，不变
        System.out.println(str3); // HelloWorld，新的字符串


        // 获取指定位置的单个字符
        char ch = "hello".charAt(1);
        System.out.println("second char is " + ch);

        //查找参数字符串在源字符串的位置
        String original = "HelloWorld";
        int index = original.indexOf("llo");
        System.out.println("first index is " + index); // 2

        System.out.println("HelloWorld".indexOf("abc")); // -1
    }
}
