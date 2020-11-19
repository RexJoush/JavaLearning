package com.joush.day02.demo02StringMethod;

/*
    字符串截取方法：
    public String substring(int index) 截取从参数为止一直到字符串末尾，返回新串
    public String substring(int begin, int end) 截取从 begin 开始，一直到 end 结束的字符串
    [begin, end)，包含左不包含右
 */

public class Demo03StringSub {
    public static void main(String[] args) {
        String str1 = "HelloWorld";
        String str2 = str1.substring(5);
        System.out.println(str1); // HelloWorld
        System.out.println(str2); // World

        String str3 = str1.substring(4,7);
        System.out.println(str3); // oWo

        // 下面这种写法，字符串的内容仍然是不变的
        // 下面有两个字符串 "Hello", "Java"
        // strA 保存的是地址值本来地址是 Hello 的 0x666，后来变成了 0x999
        String strA = "Hello";
        System.out.println(strA); // Hello
        strA = "Java";
        System.out.println(strA); // Java

    }
}
