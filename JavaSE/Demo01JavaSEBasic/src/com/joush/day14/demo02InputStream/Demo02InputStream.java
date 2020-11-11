package com.joush.day14.demo02InputStream;

import java.io.FileInputStream;
import java.io.IOException;

/*
    字节流一次读取多个字节的方法
        int read(byte[] b) 从输入流中读取一定数量的字节，并将其存储在缓冲区数组 b 中
    明确两件事情
        1.方法的参数 byte[] 的作用
            起到缓冲的作用，存储每次读取到的多个字节，一般是 1024 字节，或者是 1024 的倍数
        2.方法的返回值 int 是什么
            返回的是读取字符的个数
    String 类的构造方法
        String(byte[] bytes) 把字节数组转换为字符串
        String(byte[] bytes, int offset, int length) 把字节数组的一部分转换为字符串，offset 开始，length 转换的字符个数

 */
public class Demo02InputStream {
    public static void main(String[] args) throws IOException {


        // 创建 FileInputStream 对象，构造方法中绑定要读取的数据源
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo02InputStream\\a.txt");
        /*
        // 使用 FileInputStream 对象中的方法 read 读取文件
        byte[] bytes = new byte[2];
        int len = fis.read(bytes);
        System .out.println(len); // 2
        // System.out.println(Arrays.toString(bytes)); // [97, 98]
        System.out.println(new String(bytes)); //ab

         */

        byte[] bytes = new byte[1024];
        int len = 0;

        while ((len = fis.read(bytes)) != -1){
            System.out.println(new String(bytes,0,len));
        }
        // 释放资源
        fis.close();
    }
}
