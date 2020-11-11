package com.joush.day02.demo01String;

/*
    字符串常量池，程序中直接写上双引号字符串，就在字符串常量池中

    对于基本类型来说， == 是对于数值的比较
    对于引用类型来说， == 是对于地址值的比较



 */
public class Demo02StringPool {
    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abc";
        char[] charArray = {'a','b','c'};
        String str3 = new String(charArray);

        // 双引号直接写的在常量池中，new的字符串不在常量池中
        System.out.println(str1 == str2); // true
        System.out.println(str1 == str3); // false
        System.out.println(str3 == str2); // false
    }
}
