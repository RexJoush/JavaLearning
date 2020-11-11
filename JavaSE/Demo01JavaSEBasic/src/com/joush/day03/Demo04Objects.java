package com.joush.day03;

import java.util.Objects;

public class Demo04Objects {
    public static void main(String[] args) {
        String s1 = null;
        String s2 = "aBc";

        //boolean b = s1.equals(s2); // NullPointerException null 是不能调用方法的，就会抛出空指针异常
        /*
            Objects 类的 equals 方法也是对两个对象比较，防止空指针异常
            public static boolean equals(Object a, Object b) {
                return (a == b) || (a != null && a.equals(b));
            }
         */
        boolean b2 = Objects.equals(s1, s2);
        System.out.println(b2); // false
    }
}
