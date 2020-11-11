package com.joush.day15.demo04Encoding;

/*
    java.io.InputStreamReader extends Reader
    InputStreamReader，字节流通向字符流的桥梁，它使用指定的 charset 读取字节并将其解码为字符，解码
    继承自父类的成员方法
        - int read(); 读取单个字符并返回
        - int read(char[] cbuf); 一次读取多个字符，将字符读入数组
        - void close(); 关闭并释放资源
    构造方法
        InputStreamReader(InputStream in); 创建一个使用默认字符集的 InputStreamReader
        InputStreamReader(InputStream in, String charsetName); 创建一个使用指定字符集的 InputStreamReader
        参数
            InputStream in，字节输入流，用来读取文件中保存的字节
            String charsetName，指定编码表名称，不区分大小写，（GBK,UTF-8等，默认 UTF-8）
    使用步骤
        1.创建 InputStreamWriter 对象，构造方法参数传递字节输入流和指定的编码表名称
        2.使用 InputStreamWriter 对象的方法，read() 读取文件
        3.使用 flush 方法，将缓冲区的字符写入文件
        4.释放资源
    注意事项，构造方法中指定的编码名称要和文件的编码一致
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Demo03InputStreamReader {
    public static void main(String[] args) throws IOException {
        readGBK();
    }

    /*
        读取 GBK 文件
     */
    private static void readGBK() throws IOException {
        // 1.创建 InputStreamWriter 对象，构造方法参数传递字节输入流和指定的编码表名称
        InputStreamReader isr = new
                InputStreamReader(new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\" +
                "GBK.txt"),"GBK");

        // 2.使用 InputStreamWriter 对象的方法，read() 读取文件
        int len = 0;
        while ((len = isr.read()) != -1){
            System.out.print((char)len);
        }

        // 3.释放资源
        isr.close();
    }

    /*
        读取 UTF-8 的文件
     */
    private static void readUTF8() throws IOException {

        // 1.创建 InputStreamWriter 对象，构造方法参数传递字节输入流和指定的编码表名称
        InputStreamReader isr = new
                InputStreamReader(new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\" +
                "UTF8.txt"),"UTF-8");

        // 2.使用 InputStreamWriter 对象的方法，read() 读取文件
        int len = 0;
        while ((len = isr.read()) != -1){
            System.out.print((char)len);
        }

        // 3.释放资源
        isr.close();

    }
}
