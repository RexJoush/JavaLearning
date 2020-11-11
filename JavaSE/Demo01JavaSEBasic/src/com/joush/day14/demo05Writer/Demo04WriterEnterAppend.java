package com.joush.day14.demo05Writer;

import java.io.FileWriter;
import java.io.IOException;

/*
    续写和换行
    续写，使用两个参数的构造方法
        FileWriter(File file, boolean append) 根据给定的 File 对象构造一个 FileWriter 对象，append，续写
        FileWriter(String fileName,boolean append) 根据给定的文件名构造一个 FileWriter 对象，append，续写
    换行，换行符号
        windows \r\n
        linux /n
        mac /r
 */
public class Demo04WriterEnterAppend {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo05Writer\\a.txt",true);
        for (int i = 0; i < 10; i++) {
            fw.write("HelloWorld" + i + "\r\n");
        }

        fw.close();
    }
}
