package com.my.day18.demo04ArrayReference;

import java.util.Arrays;

/*
    数组的构造器引用
 */
public class Demo01ArrayConductorReference {

    /*
        创建一个方法，方法的参数传递创建数组的长度和 ArrayBuilder 接口
        方法内部根据传递的长度返回一个 int 型数组
     */

    public static int[] creatArray(int length, ArrayBuilder ab) {
        return ab.getIntArray(length);
    }

    public static void main(String[] args) {
        // 调用 creatArray 方法
        int[] ints = creatArray(10, (length) -> {
            return new int[length];
        });

        System.out.println(ints.length); // 10

        /*
            使用方法引用优化 Lambda 表达式
                1.已知创建的数组 int[]
                2.数组长度已知
            可以使用方法引用创建数组
         */
        int[] arr = creatArray(10, int[]::new);
        System.out.println(Arrays.toString(arr));
        System.out.println(arr.length); // 10

    }

}
