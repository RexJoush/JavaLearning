package com.joush.day14.demo05Writer;

import java.io.FileWriter;
import java.io.IOException;

/*
    java.io.Writer 字符输出流，是所有字符输出流最顶层的父类，是一个抽象类
    共性的成员方法
        - void write(int c) 写入单个字符
        - void write(char[] cbuf) 写入字符数组
        - abstract void write(char[] cbuf, int off, int len) 写入字符数组的某一部分 off 开始，len 个
        - void write(String str) 写入字符串
        - void write(String str, int off, int length) 写入字符串的某一部分， off 字符串的开始索引，len 长度
        - void flush() 刷新该流的缓冲
        - void close() 关闭流，需先刷新
    java.io.FileWriter extends OutputStreamWriter extends Writer
    FileWriter，文件字符输出流
    作用，把内存中的字符数据写入硬盘
    构造方法：
        FileWriter(File file) 根据给定的 File 对象构造一个 FileWriter 对象
        FileWriter(String fileName) 根据给定的文件名构造一个 FileWriter 对象
        参数，写入文件的目的地
            String fileName，文件路径
            File file，一个文件
    字符输出流的使用步骤
        1.创建 FileWriter 对象，构造方法中要绑定写入数据的目的地
        2.使用 FileWriter 中的方法 write，把数据写入到内存缓冲区中
        3.使用 FileWrite 中的方法 flush，把内存缓冲区的数据，刷新到文件中
        4.释放资源（会先把内存缓冲区的数据，刷新到文件中）
 */
public class Demo01Writer {
    public static void main(String[] args) throws IOException {
        // 1.创建 FileWriter 对象，构造方法中要绑定写入数据的目的地
        FileWriter fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo05Writer\\a.txt");
        // 2.使用 FileWriter 中的方法 write，把数据写入到内存缓冲区中
        // void write(int c) 写入单个字符
        fw.write(97);
        // 3.使用 FileWrite 中的方法 flush，把内存缓冲区的数据，刷新到文件中
        // fw.flush();
        // 4.释放资源（会先把内存缓冲区的数据，刷新到文件中）
        fw.close();
    }

}
