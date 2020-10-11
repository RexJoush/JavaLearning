package com.my.day02.demo02StringMethod;

/*
    题目：
    定义一个方法，把数组｛1，2，3｝按照指定格式拼接成一个字符串
    格式如下 [word1#word2#word3]
 */
public class Demo06StringPractise {
    public static void main(String[] args) {
        int[] array = {1,2,3};
        String str = fromArrayToString(array);
        System.out.println(str);
    }

    public static String fromArrayToString(int[] array){
        String result = "[";
        for (int i = 0; i < array.length - 1; i++) {
            result += array[i];
            result += "#";
        }
        return result + array[array.length-1] + "]";
    }
}
