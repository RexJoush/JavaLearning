package com.my.day10.demo03MyException;

/*
    如果 finally 中有 return 语句，永远返回 finally 中的结果，避免该情况
 */
public class Demo02Exception {
    public static void main(String[] args) {
        int a = getA();
        System.out.println(a); // 100
    }

    // 定义一个方法，返回变量的值
    public static int getA(){
        int a = 10;
        try {
            return a;
        } catch (Exception e){
            System.out.println(e);
        } finally {
            a = 100;
            return a;
        }
    }

}
