package com.joush.day02.demo02StringMethod;

/*
    public boolean equals(Object obj) 参数可以是任何对象，只有参数是一个字符串且内容相同才会返回 true，否钻返回 false
    1.任何对象都可以使用object接收
    2.equals 具有对称性，a.equals(b) 和 b.equals(a) 是一样的
    3.如果比较双方一个常量，一个变量，推荐把常量字符串写在前面
       推荐： "abc".equals(str);
       不推荐： str.equals("abc");
    public boolean equalsIgnoreCase(String str) 忽略大小写比较
 */
public class Demo01StringEquals {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = "Hello";
        char[] charArray = {'H','e','l','l','o'};
        String str3 = new String(charArray);
        System.out.println(str1.equals(str2)); // true
        System.out.println(str1.equals(str3)); // true
        System.out.println(str2.equals(str3)); // true

        String str4 = "abc";
        System.out.println("abc".equals(str4)); // 推荐
        System.out.println(str4.equals("abc")); // 不推荐

        String str5 = null;
        System.out.println("abc".equals(str4)); // 推荐,不会出现错误，会返回false
        //System.out.println(str5.equals("abc")); // 不推荐，会出现空指针异常，NullPointerException

        // 忽略大小写比较

        String stringA = "Java";
        String stringB = "java";
        System.out.println(stringA.equals(stringB));
        System.out.println(stringA.equalsIgnoreCase(stringB));

    }
}
