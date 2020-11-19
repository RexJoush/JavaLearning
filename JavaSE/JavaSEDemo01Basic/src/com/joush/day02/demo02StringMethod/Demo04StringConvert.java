package com.joush.day02.demo02StringMethod;

/*
    String 中与转换有关的方法

    public char[] toCharArray() 将当前字符串拆分成字符数组返回
    public byte[] getBytes() 获得当前字符串的底层的字节数据
    public String replace(charSequence oldString, charSequence newString)
        将所有出现的 old 字符串，替换成 new 字符串，返回新串
        // charSequence 可以暂时当作 String 使用
 */
public class Demo04StringConvert {
    public static void main(String[] args) {
        // 转换成字符数组
        char[] chars = "Hello".toCharArray();
        System.out.println(chars[0]); // H
        System.out.println(chars.length); //5

        // 转换成字节数组
        byte[] bytes = "abc".getBytes();
        for (int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }

        // 字符串替换
        String str1 = "How do you do?";
        String str2 = str1.replace("o","*");
        System.out.println(str1); // How do you do?
        System.out.println(str2); // H*w d* y*u d*?
    }
}
