package com.my.day14.demo04Reader;

import java.io.FileInputStream;
import java.io.IOException;

/*
    使用字节流读取中文字体
    1个中文
        GBK 占用 2 个字节
        UTF-8 占用 3 个字节
 */
public class Demo01InputStream {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo01OutputStream\\c.txt");

        int len = 0;
        while ((len = fis.read()) != -1){
            System.out.println((char)len); // 读取中文会产生乱码
        }

        fis.close();
    }
}
