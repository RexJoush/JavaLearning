package com.joush.day14.demo01OutputStream;

import java.io.FileOutputStream;
import java.io.IOException;

/*
    追加/续写，使用两个参数的构造方法
        FileOutputStream(String name, boolean append) 创建一个向具有指定 name 的文件中写入数据的输出文件流
        FileOutputStream(File file, boolean append) 创建一个向具有指定 file 的文件中写入数据的输出文件流
        参数
            String name, File file：写入数据目的地
            boolean append 追加写开关
                true，创建对象不会覆盖原文件，继续追加
                false，创建新文件覆盖原文件，继续写文件
    写换行：写换行符
        windows \r\n
        linux /n
        mac /r
 */
public class Demo03OutputStream {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo01OutputStream\\c.txt",true);

        for (int i = 0; i < 10; i++) {
            fos.write("你好".getBytes());
            fos.write("\r\n".getBytes());
        }
        fos.close();
    }
}
