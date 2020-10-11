package com.my.day04.demo01Date;

/*
    java.util.Calender 日历类，抽象类，无法实例化。提供了很多操作日历的方法
        有一个静态方法，
        static Calender getInstance(), 返回一个 Calender 类的实例
    Calender 类常用成员方法
        public int get (int field) 返回给定日历字段的值
        public void set (int field, int value) 将给定的日历字段设为给定值
        public abstract void add (int filed, int amount) 根据日历的规则，给定日历字段添加或减去给定的时间量
        public Date getTime() 返回一个表示 Calender 时间值的 Date 对象
    成员方法的参数
        int filed 日历的字段，可以使用 Calender 类的静态成员标量
        public final static int YEAR = 1;   年
        public final static int MONTH = 2;  月
        public final static int DATE = 5;   月
        public final static int DAY_OF_MONTH = 5;   月中某一天
        public final static int HOUR = 10;  时
        public final static int MINUTE = 12; 分
        public final static int SECOND = 13; 秒
 */

import java.util.Calendar;
import java.util.Date;

public class Demo04Calender {
    public static void main(String[] args) {
        // 返回一个 Calender 类的实例
        Calendar c = Calendar.getInstance(); // 多态
        System.out.println(c);
        demo04();
    }

    /*
        public Date getTime() 把日历对象变为日期对象
     */
    private static void demo04() {
        Calendar c = Calendar.getInstance();

        Date date = c.getTime();
        System.out.println(date);
    }

    /*
        public abstract void add (int filed, int amount) 把指定的字段增加或减少
        参数： int filed 指定字段，int amount 指定值
     */
    private static void demo03() {
        Calendar c = Calendar.getInstance();
        // 把年增加两年
        c.add(Calendar.YEAR,2);

        // 把月减少3个月
        c.add(Calendar.MONTH,-3);
    }

    /*
        public void set(int filed, int value) 将给定的字段设置为指定的值
        参数： 传递指定的日历字段，和 value
     */
    private static void demo02() {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR,2099);
        c.set(Calendar.MONTH,11);
        c.set(Calendar.DATE,13);

        // 同时设置年月日
        c.set(8888,8,8);
    }

    /*
        public int get(int filed) 返回给定日历字段的值
            参数：传递指定的日历字段（YEAR,MOUTH...）
            返回值：日历字段代表的值
     */
    private static void demo01() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        System.out.println(year); // 2020

        int mouth = c.get(Calendar.MONTH);
        System.out.println(mouth); // 2,此处是西方的月份，0-11，可以 +1

        //int date = c.get(Calendar.DAY_OF_MONTH);
        int date = c.get(Calendar.DATE); // 两种均可
        System.out.println(date); // 4
    }
}
