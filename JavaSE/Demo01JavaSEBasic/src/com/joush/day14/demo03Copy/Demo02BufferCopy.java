package com.joush.day14.demo03Copy;

import java.io.*;

/*
    使用缓冲区实现文件复制
 */
public class Demo02BufferCopy {
    public static void main(String[] args) throws IOException {

        long s = System.currentTimeMillis();

        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo02InputStream\\1.png");
        FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo03Copy\\1.png");

        BufferedInputStream bif = new BufferedInputStream(fis);
        BufferedOutputStream buf = new BufferedOutputStream(fos);

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = bif.read(bytes)) != -1){
            buf.write(bytes,0,len);
        }

        long e = System.currentTimeMillis();

        System.out.println("耗时：" + (e-s));

    }
}
