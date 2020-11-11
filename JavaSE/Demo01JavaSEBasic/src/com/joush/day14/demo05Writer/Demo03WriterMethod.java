package com.joush.day14.demo05Writer;

import java.io.FileWriter;
import java.io.IOException;

/*
    字符输出流写数据的其他方法
        - void write(char[] cbuf) 写入字符数组
        - abstract void write(char[] cbuf, int off, int len) 写入字符数组的某一部分 off 开始，len 个
        - void write(String str) 写入字符串
        - void write(String str, int off, int length) 写入字符串的某一部分， off 字符串的开始索引，len 长度
 */
public class Demo03WriterMethod {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo05Writer\\a.txt");
        char[] chars = {'a','b','c','d','e'};
        // void write(char[] cbuf) 写入字符数组
        fw.write(chars); // abcde

        // abstract void write(char[] cbuf, int off, int len) 写入字符数组的某一部分 off 开始，len 个
        fw.write(chars,1,3); // bcd

        // void write(String str) 写入字符串
        fw.write("aaaaa"); // aaaaa

        // void write(String str, int off, int length) 写入字符串的某一部分， off 字符串的开始索引，len 长度
        fw.write("aaabbb",3,3); // bbb

        fw.close();

    }
}
