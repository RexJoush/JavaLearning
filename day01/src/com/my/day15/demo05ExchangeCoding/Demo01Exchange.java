package com.my.day15.demo05ExchangeCoding;

import java.io.*;

/*
    转换文件编码，将 GBK 转换成 UTF-8
 */
public class Demo01Exchange {
    public static void main(String[] args) throws IOException {
        // 1.创建 InputStreamReader 对象。构造方法中传递字节输入流和指定的编码表名称 GBK
        InputStreamReader isr = new
                InputStreamReader(new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\" +
                "GBK.txt"),"GBK");

        // 2.创建 OutputStreamWriter 对象，构造方法传入字节输出流和指定编码表名称 UTF-8
        OutputStreamWriter osw = new OutputStreamWriter(new
                FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\" +
                "UTF8.txt"),"UTF-8");

        // 3.使用 InputStreamReader 对象的方法 read 读取文件
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = isr.read()) != -1){
            // 4.使用 OutputStreamWriter 对象中的方法，把读取的数据写入到文件中
            osw.write(len);
        }

        // 5.释放资源
        osw.close();
        isr.close();
    }
}
