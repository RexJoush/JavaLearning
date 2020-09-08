package com.my.day14.demo03Copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    文件复制的练习
 */
public class Demo01Copy {
    public static void main(String[] args) throws IOException {

        long s = System.currentTimeMillis();

        // 创建文件读取流
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo02InputStream\\1.png");
        FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo03Copy\\1.png");

        int len = 0;
        byte[] bytes = new byte[1024];
        // 读取文件
        while ((len = fis.read(bytes)) != -1){
            //System.out.println(new String(bytes,0,len));
            fos.write(bytes,0,len);
        }

        fos.close();
        fis.close();

        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e-s));

    }
}