package com.joush.day03;

/*
    java.util.Math 类是工具类，提供了大量与数学有关的静态方法

    public static double abs(double num) 获取绝对值
    public static double ceil (double num) 向上取整
    public static double floor (double num) 向下取整
    public static long round (double num) 四舍五入

    Math.PI 代表近似的圆周率常量 返回 double 值
 */
public class Demo02Math {
    public static void main(String[] args) {
        // 获取绝对值
        System.out.println(Math.abs(3.14)); // 3.14
        System.out.println(Math.abs(-3.14)); // 3.14

        // 向上取整
        System.out.println(Math.ceil(3.9)); // 4.0
        System.out.println(Math.ceil(3.0)); // 3.0

        // 向下取整
        System.out.println(Math.floor(3.14)); //3.0

        // 四舍五入
        System.out.println(Math.round(20.4)); // 20
        System.out.println(Math.round(10.5)); // 11
    }
}
