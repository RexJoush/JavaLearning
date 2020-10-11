package com.my.day14.demo01OutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/*
    一次写多个字节的方法：
        - public void write(byte[] b); 将 b.length 字节从指定的字节数组写入值输出流
        - public void write(byte[] b, int off, int len); 将指定的字节数组写入 len 字节，从偏移量 off 开始输出到此输出流
 */
public class Demo02OutputStream {
    public static void main(String[] args) throws IOException {
        // 创建 FileOutputStream 对象，构造方法中绑定要写入数据的目的地
        FileOutputStream fos = new FileOutputStream(new File("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo01OutputStream\\a.txt"));
        // 调用 FileOutputStream 对象中的方法 write，把数据写入到文件中
        // 在文件中显示 100，需要写几个字节
        fos.write(49);
        fos.write(48);
        fos.write(48);

        /*
            public void write(byte[] b); 将 b.length 字节从指定的字节数组写入此输出流
            一次写多个字节
                如果写的第一个字节是正数（0-127），那么显示的时候会查询 ASCII 表
                如果写的第一个字节是负数，那么第一个字节会和第二个字节两个字节组成一个中文显示，查询系统默认码表（GBK）
         */

        byte[] bytes = {65,66,67,68,69}; // ABCDE
        byte[] bytes2 = {-65,66,-67,68,69}; // 緽紻E
        fos.write(bytes2);

        /*
            public void write(byte[] b, int off, int len);
            将指定的字节数组写入 len 字节，从偏移量 off 开始输出到此输出流
            int off 数组的开始索引
            int len 写几个字节
         */
        fos.write(bytes,1,2); // BC

        /*
            写入字符的方法，可以使用 String 类中的方法把字符串，转换为字节数组
            byte[] getBytes(); 把字符串转换为字节数组
         */
        byte[] bytes1 = "你好".getBytes();
        System.out.println(Arrays.toString(bytes1)); // [-28, -67, -96, -27, -91, -67]
        fos.write(bytes1);
        fos.close();

    }
}
