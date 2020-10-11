package com.my.day15.demo04Encoding;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
    java.io.OutputStreamWriter extends Writer
    OutputStreamWriter 是字符流通向字节流的桥梁，可使用指定的 charset 将要写入流中的字符编码成字节，也称为编码

    继承自父类的共有方法
        - void write(int c) 写入单个字符
        - void write(char[] cbuf) 写入字符数组
        - abstract void write(char[] cbuf, int off, int len) 写入字符数组的某一部分 off 开始，len 个
        - void write(String str) 写入字符串
        - void write(String str, int off, int length) 写入字符串的某一部分， off 字符串的开始索引，len 长度
        - void flush() 刷新该流的缓冲
        - void close() 关闭流，需先刷新
    构造方法
        OutputStreamWriter(OutputStream out); 构造默认字符编码的 OutputStreamWriter
        OutputStreamWriter(OutputStream out, String charsetName); 构造指定字符编码的 OutputStreamWriter
        参数
            OutputStream out，字节输出流，可以用来写转换之后字节到文件中
            String charsetName，指定编码表名称，不区分大小写，（GBK,UTF-8等，默认 UTF-8）
    使用步骤
        1.创建 OutputStreamWriter 对象，构造方法参数传递字节输出流和指定的编码表名称
        2.使用 OutputStreamWriter 对象的方法，write()，把字符串转换成字节，存储到缓冲区
        3.使用 flush 方法，将缓冲区的字符写入文件
        4.释放资源
 */
public class Demo02OutputStreamWriter<ps> {

    public static void main(String[] args) throws IOException {
        writeUTF8();
    }

    private static void writeGBK() throws IOException {
        // 1.创建 OutputStreamWriter 对象，构造方法参数传递字节输出流和指定的编码表名称
        OutputStreamWriter osw = new OutputStreamWriter(new
                FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\" +
                "GBK.txt"),"GBK");
        // 2.使用 OutputStreamWriter 对象的方法，write()，把字符串转换成字节，存储到缓冲区
        osw.write("你好");

        // 3.使用 flush 方法，将缓冲区的字符写入文件
        osw.flush();

        // 4.释放资源
        osw.close();
    }

    private static void writeUTF8() throws IOException {
        // 1.创建 OutputStreamWriter 对象，构造方法参数传递字节输出流和指定的编码表名称
        OutputStreamWriter osw = new OutputStreamWriter(new
                FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\" +
                "UTF8.txt"),"utf-8");
        // 2.使用 OutputStreamWriter 对象的方法，write()，把字符串转换成字节，存储到缓冲区
        osw.write("你好");

        // 3.使用 flush 方法，将缓冲区的字符写入文件
        osw.flush();

        // 4.释放资源
        osw.close();
    }
}
