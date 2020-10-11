package com.my.day14.demo02InputStream;

/*
    java.io.InputStream 字节输入流
    此抽象类是表示字节输入流的所有类的超类

    定义了所有子类共性的方法
        int read() 从输入流中读取数据的下一个字节
        int read(byte[] b) 从输入流中读取一定数量的字节，并将其存储在缓冲区数组 b 中
        void close() 关闭输入流并释放与该流关联的所有系统资源

    java.io.FileInputStream extends InputStream
    FileInputStream 文件字节流
    作用
        把硬盘文件中的数据，读取到内存中使用
    构造方法
        FileInputStream(String name);
        FileInputStream(File file);
        参数：读取文件的数据源
            String name 文件路径
            File file 文件
     使用步骤
        1.创建 FileInputStream 对象，构造方法中绑定要读取的数据源
        2.使用 FileInputStream 对象中的方法 read，读取文件
        3.释放资源
 */

import java.io.FileInputStream;
import java.io.IOException;

public class Demo01InputStream {
    public static void main(String[] args) throws IOException {
        // 1.创建 FileInputStream 对象，构造方法中绑定要读取的数据源
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo02InputStream\\a.txt");
        // 2.使用 FileInputStream 对象中的方法 read，读取文件
        // int read() 读取文件中的一个字节并返回，读取文件末尾返回 -1

        /*
        int len1 = fis.read();
        System.out.println(len1); // 97 a
        int len2 = fis.read();
        System.out.println(len2); // 98 b
        int len3 = fis.read();
        System.out.println(len3); // 99 c
        int len4 = fis.read();
        System.out.println(len4); // -1
         */

        // 循环优化
        int len = 0;
        while ((len = fis.read()) != -1){
            System.out.println((char) len); // a b c
        }
        
        // 3.释放资源
        fis.close();
    }
}
