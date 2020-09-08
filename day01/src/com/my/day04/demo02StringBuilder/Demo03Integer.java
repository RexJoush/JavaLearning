package com.my.day04.demo02StringBuilder;

/*
    基本类型与字符串直接进行转换
    基本类型 --> 字符串
        1.基本类型的值 + "",最简单的方法
        2.包装类的静态方法 toString(参数)，不是 Object 类的 toString() 重写
            static String toString(int i) 返回一个表示指定整数的 String 对象
        3.String 类的静态方法 valueOf(int i) 返回 int 参数的字符串
     字符串 --> 基本类型
        使用包装类的静态方法 parseXX("字符串")
            Integer static int parseInt(String str)
            Double static int parseDouble(String str)
 */
public class Demo03Integer {
    public static void main(String[] args) {
        // 基本类型 --> 字符串
        int i1 = 100;
        String s1 = i1 + "";
        System.out.println(s1 + 200); // 100200

        // 字符串 --> 基本类型
        int i = Integer.parseInt(s1);
        System.out.println(i - 10); // 90
    }
}
