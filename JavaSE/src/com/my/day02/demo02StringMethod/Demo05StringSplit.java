package com.my.day02.demo02StringMethod;

/*
    切割字符串方法
    public String[] split (String regex); 按照参数的规则，将字符串切割成若干部分
    注意事项
    split 方法其实是一个正则表达式，如果按照 . 切分，所以要用 "//."替代
 */
public class Demo05StringSplit {
    public static void main(String[] args) {
        String str1 = "aaa,bbb,ccc";
        String[] array1 = str1.split(",");
        for (int i = 0; i < array1.length; i++) {
            System.out.println(array1[i]);
        }

        String str2 = "aaa bbb ccc";
        String[] array2 = str2.split(" ");
        for (int i = 0; i < array2.length; i++) {
                System.out.println(array2[i]);
        }

        String str3 = "XXX.YYY.ZZZ";
        String[] array3 = str3.split("//.");
        for (int i = 0; i < array3.length; i++) {
            System.out.println(array3[i]);
        }
    }
}
