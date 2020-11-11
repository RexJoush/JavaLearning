package com.joush.day04.demo01Date;

import java.util.Date;

public class Demo02Date {
    public static void main(String[] args) {
        demo01();
        demo02();
        demo03();
    }

    // Date 类空参构造方法
    private static void demo01() {
        Date date = new Date();
        System.out.println(date);
    }

    // Date 类的带参构造方法
    // Date (long date) {} 传递毫秒值，把毫秒转换为日期
    private static void demo02(){
        Date date = new Date(0L);
        System.out.println(date);
    }

    // long getTime() 把日期转换为毫秒值 相当于 System.currentTimeMillis() 方法
    private static void demo03() {
        Date date = new Date();
        long time = date.getTime();
        System.out.println(time); // 1583315820407
    }

}
