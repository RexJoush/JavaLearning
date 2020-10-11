package com.my.day01.demo01Scanner;

import java.util.Scanner;

public class Demo01Scanner {
    public static void main(String[] args) {
        // 创建
        // System.in 表示从键盘输入
        Scanner sc = new Scanner(System.in);

        // 获取数字
        int num = sc.nextInt();
        System.out.println("输入的int数字是: " + num);

        // 获取字符串
        String str = sc.next();
        System.out.println("输入的字符串是: " + str);

    }
}
