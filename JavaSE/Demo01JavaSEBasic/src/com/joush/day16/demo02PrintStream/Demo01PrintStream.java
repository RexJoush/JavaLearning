package com.joush.day16.demo02PrintStream;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/*
    java.io.PrintStream 打印流
        PrintStream 为其他流输出添加了功能，使它们能够方便的法因各种数据值表示形式
    PrintStream 特点
        1.只负责数据的输出，不负责读取
        2.永远不会抛出 IOException
        3.有特有的方法， print，println 方法等
            void print(任意类型);
            void println(任意类型 + 换行);
    构造方法
        PrintStream(File file); 输出目的地是个文件
        PrintStream(OutputStream out); 输出目的低是一个字节输出流
        PrintStream(String fileName); 输出的目的地是一个文件路径

    PrintStream extends OutputStream
    继承自父类的成员方法
        - public void close(); 关闭此输出流并释放与此流相关联任何系统资源
        - public void flush(); 刷新此输出流并强制任何缓冲的输出字节被写出
        - public void write(byte[] b); 将 b.length 字节从指定的字节数组写入值输出流
        - public void write(byte[] b, int off, int len); 将指定的字节数组写入 len 字节，从偏移量 off 开始输出到此输出流
        - public abstract void write(int b); 将指定的字节输出流

    注意事项
        如果使用继承自父类的 write 方法写数据，那么查看数据的时候会查询编码表
        如果使用自己特有的 print，println 方法，那么写的数据原样输出
 */
public class Demo01PrintStream {
    public static void main(String[] args) throws FileNotFoundException {
        // 创建对象
        PrintStream ps = new PrintStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day16\\demo02PrintStream\\a.txt");

        // 如果使用继承自父类的 write 方法写数据，那么查看数据的时候会查询编码表
        ps.write(97); // 97 -> a
        ps.println(97); // 97

        // 释放资源
        ps.close();

    }
}
