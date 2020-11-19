package com.joush.day17.demo04Lambda;

import java.util.Arrays;
import java.util.Comparator;

/*
    如果一个方法的返回值是一个函数式接口，那么可以直接返回一个 Lambda 表达式
    当需要通过一个方法来获取一个 java.util.Comparator 接口类型的对象作为排序器时，就可以调该方法获取
 */
public class Demo03Comparator {

    // 定义一个方法，返回值类型使用函数式接口 Comparator
    public static Comparator<String> getComparator(){

        /*
            按照字符串的长度排序
         */

        // 方法的返回值是一个函数式接口我们可以返回这个接口的匿名内部类
        /*return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 按照字符串的降序排序
                return o2.length() - o1.length();
            }
        };*/

        // 方法的返回值是一个函数式接口，那么可以直接返回一个 Lambda 表达式
        /*return (String o1, String o2)->{
            return o2.length() - o1.length();
        };*/


        // 优化的 Lambda 表达式
        return (o1,o2) -> o2.length() - o1.length();

    }

    public static void main(String[] args) {
        String[] arr = {"aaa","b","ccc","dddddd"};

        // 排序前
        System.out.println(Arrays.toString(arr));

        // 排序
        Arrays.sort(arr,getComparator());

        // 排序后
        System.out.println(Arrays.toString(arr));
    }

}
