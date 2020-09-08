package com.my.day04.demo02StringBuilder;

/*
    java.lang.StringBuilder 类，字符串缓冲区，可以提高字符串的效率
    构造方法
        StringBuilder() ,构造一个不带参数的字符串生成器，初始容量为16个字符
        StringBuilder(String str) 构造一个字符串生成器，并初始化为指定的字符串内容

 */
public class Demo01StringBuilder {
    public static void main(String[] args) {
        // 空参构造方法
        StringBuilder bu1 = new StringBuilder();
        System.out.println("bu1" + bu1); // bu1

        StringBuilder bu2 = new StringBuilder("abc");
        System.out.println("bu2" + bu2); // bu2abc
    }

}
