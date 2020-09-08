package com.my.day10.demo01Exception;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    java.lang.Throwable 是 java 中所有错误或异常的超类
        Exception 称为编译器异常，
            RuntimeException 运行期异常，java 运行时出现的问题
        Error
            错误是程序崩溃
 */
public class Demo01Exception {

    public static void main(String[] args) /*throws ParseException*/ {

        // 编译期异常
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse("1997-08-17");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 运行期异常
        int[] arr = {1,2,3};
        try {
            System.out.println(arr[3]);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("后续代码");

        /*
            Error 错误
         */
        // int[] arr2 = new int[1024*1024*1024];
        // java.lang.OutOfMemoryError 错误

    }

}
