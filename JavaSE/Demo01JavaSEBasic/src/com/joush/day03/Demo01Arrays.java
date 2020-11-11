package com.joush.day03;

import java.util.Arrays;

/*
    java.util.Arrays 是一个与数组相关的工具类，里面提供了大量静态方法，用来实现数组常见的操作。

    public static String toString (<E>[] array) 将参数数组变成字符串 （按照默认格式， [元素一，元素二 ...]）
    public static void sort (<E>[] array) 按照默认升序对数组元素进行排序

    1.如果是数值，默认按照从小到大
    2.如果是字符串，默认字典序
    3.如果是自定义类型， 需要有 Comparable 或者 Comparator 接口的支持
 */
public class Demo01Arrays {

    public static void main(String[] args) {
        int[] intArray = {10,20,30};

        String intStr = Arrays.toString(intArray);
        System.out.println(intStr); // [10,20,30]

        int[] array1 = {2,1,3,10,6};
        Arrays.sort(array1);
        System.out.println(Arrays.toString(array1));
    }

}
