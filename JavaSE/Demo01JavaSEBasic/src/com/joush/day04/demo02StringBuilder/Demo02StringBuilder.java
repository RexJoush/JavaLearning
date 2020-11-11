package com.joush.day04.demo02StringBuilder;

/*
    StringBuilder 常用方法
        public StringBuilder append(...) 添加任意类型数据的字符串形式，并返回当前对象自身。
 */
public class Demo02StringBuilder {
    public static void main(String[] args) {
        StringBuilder bu = new StringBuilder();

        StringBuilder bu2 = bu.append("123");
        System.out.println(bu); // 123
        System.out.println(bu2); // 123
        System.out.println(bu == bu2); // append 返回 this

        // 使用 append 不用返回值
        bu.append("abc").append(12).append(true).append(8.8).append("中");
    }
}
