package com.joush.day14.demo04Reader;

import java.io.FileReader;
import java.io.IOException;

/*
    java.io.Reader 字符输入流，是字符输入流最顶层的父类，定义了一些共性的方法，是一个抽象类
    共性的成员方法
        - int read(); 读取单个字符并返回
        - int read(char[] cbuf); 一次读取多个字符，将字符读入数组
        - void close(); 关闭并释放资源
    java.io.FileReader extends InputStreamReader extends Reader
    FileReader 文件字符输入流
    作用
        把硬盘文件中的数据以字符的方式读取到内存中
    构造方法
        1.FileReader(String fileName);
        2.FileReader(File file);
        参数，读取的数据源
        String fileName，文件路径
        File file，一个文件
    字符输入流的使用步骤
        1.创建 FileReader 对象，构造方法中绑定要读取的数据源
        2.使用 FileReader 对象中的方法 read 读取文件
        3.释放资源
 */
public class Demo02Reader {
    public static void main(String[] args) throws IOException {
        // 1.创建 FileReader 对象，构造方法中绑定要读取的数据源
        FileReader fr = new FileReader("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo01OutputStream\\c.txt");

        // 2.使用 FileReader 对象中的方法 read 读取文件
        /*
        int len = 0;
        while ((len = fr.read()) != -1){
            System.out.print((char)len);
        }
        */

        char[] chars = new char[1024];
        int len = 0; // 记录的是每次读取的有效字符的个数
        while ((len = fr.read(chars)) != -1){
            System.out.println(new String(chars,0,len));
        }


        // 3.释放资源
        fr.close();

    }
}
