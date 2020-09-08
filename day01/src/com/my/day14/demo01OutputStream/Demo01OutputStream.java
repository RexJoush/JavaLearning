package com.my.day14.demo01OutputStream;

import java.io.FileOutputStream;
import java.io.IOException;

/*
    java.io.OutputStream，字节输出流
        此抽象类是表示输出字节流的所有类的超类
    定义了一些共性成员方法：
        - public void close(); 关闭此输出流并释放与此流相关联任何系统资源
        - public void flush(); 刷新此输出流并强制任何缓冲的输出字节被写出
        - public void write(byte[] b); 将 b.length 字节从指定的字节数组写入值输出流
        - public void write(byte[] b, int off, int len); 将指定的字节数组写入 len 字节，从偏移量 off 开始输出到此输出流
        - public abstract void write(int b); 将指定的字节输出流
    java.io.FileOutputStream extends OutputStream
    FileOutputStream，文件字节输出流
        把内存中的数据写入硬盘文件中
    构造方法
        FileOutputStream(String name) 创建一个向具有指定名称的文件写入数据的输出文件流
        FileOutputStream(File file) 创建一个向指定 File 对象表示的文件中写入数据的文件输出流
        参数，写入数据的目的地
            String name，目的地的路径
            File file，目的地是一个文件
        作用：
            1.创建一个 FileOutputStream 对象
            2.会根据构造方法中传递的文件/文件路径，创建一个空的文件
            3.会把 FileOutputStream 对象指向创建好的文件

 */
public class Demo01OutputStream {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo01OutputStream\\a.txt");
        fos.write(97);
        fos.close();
    }
}
