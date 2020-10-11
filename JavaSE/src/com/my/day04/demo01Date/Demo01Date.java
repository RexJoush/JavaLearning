package com.my.day04.demo01Date;

/*
    java.util.Date 是表示日期和时间的类，精确到毫秒

    毫秒值的作用，可以对时间和日期进行计算
    当前日期 2020.03.04
    时间原点 1971.01.01 00:00:00 (GMT)，格林尼治时间
    注意，中国属于东八时区，会在 GMT 基础上加八个小时，即 1971.01.01 08:00:00 (GMT+8)
 */
public class Demo01Date {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis()); // 获取当前系统时间到 1970.1.1 00:00:00 年的毫秒数
    }
}
