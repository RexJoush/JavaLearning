package com.joush.day15.demo03BufferedChar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
    java.io.BufferedWriter extends Writer
    BufferedWriter 字符缓冲输出流
    继承自父类的共性成员方法
        - void write(int c) 写入单个字符
        - void write(char[] cbuf) 写入字符数组
        - abstract void write(char[] cbuf, int off, int len) 写入字符数组的某一部分 off 开始，len 个
        - void write(String str) 写入字符串
        - void write(String str, int off, int length) 写入字符串的某一部分， off 字符串的开始索引，len 长度
        - void flush() 刷新该流的缓冲
        - void close() 关闭流，需先刷新
    构造方法：
        BufferedWriter(Writer out)，创建一个使用默认大小输出缓冲区的字符输出流
        BufferedWriter(Writer out，int size)，创建一个指定大小输出缓冲区的字符输出流
    特有的成员方法
        void newLine() 写入一个行分隔符，会根据不同的操作系统获取不同的行分隔符

 */
public class Demo01BufferedWriter {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo03BufferedChar\\prop.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(97);
        bw.newLine();
        bw.write(98);
        bw.newLine();

        bw.close();
    }
}
