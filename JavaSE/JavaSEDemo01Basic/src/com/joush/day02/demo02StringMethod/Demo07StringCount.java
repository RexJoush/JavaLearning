package com.joush.day02.demo02StringMethod;

import java.util.Scanner;

/*
    题目：
    键盘输入一个字符串，统计其中各种字符出现的次数
    种类有，大写，小写，数字，其他。
 */
public class Demo07StringCount {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        char[] chars = str.toCharArray();
        int bigCase = 0;
        int smallCase = 0;
        int number = 0;
        int other = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= '0' && chars[i] <= '9'){
                number++;
            }
            else if (chars[i] >= 'a' && chars[i] <= 'z'){
                smallCase++;
            }
            else if (chars[i] >= 'A' && chars[i] <= 'Z'){
                bigCase++;
            }
            else {
                other++;
            }
        }

        System.out.println("数字有: " + number);
        System.out.println("小写字母: " + smallCase);
        System.out.println("大写字母: " + bigCase);
        System.out.println("其他:  " + other);

    }
}
