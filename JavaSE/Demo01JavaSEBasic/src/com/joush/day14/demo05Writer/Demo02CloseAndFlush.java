package com.joush.day14.demo05Writer;

import java.io.FileWriter;
import java.io.IOException;

/*
    Flush 方法和 close 方法的区别
        - flush，刷新缓冲区，流对象可以继续使用
        - close，先刷新缓冲区，然后通知系统释放资源，流对象不可以再使用
 */
public class Demo02CloseAndFlush {
    public static void main(String[] args) throws IOException {
        // 1.创建 FileWriter 对象，构造方法中要绑定写入数据的目的地
        FileWriter fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo05Writer\\a.txt");
        // 2.使用 FileWriter 中的方法 write，把数据写入到内存缓冲区中
        // void write(int c) 写入单个字符
        fw.write(97);
        // 3.使用 FileWrite 中的方法 flush，把内存缓冲区的数据，刷新到文件中
        fw.flush();

        // 刷新之后可以继续使用
        fw.write(98);

        // 4.释放资源（会先把内存缓冲区的数据，刷新到文件中）
        fw.close();

        // close 已经关闭了，所以不能再使用了
        fw.write(99); // java.io.IOException: Stream closed
    }
}
