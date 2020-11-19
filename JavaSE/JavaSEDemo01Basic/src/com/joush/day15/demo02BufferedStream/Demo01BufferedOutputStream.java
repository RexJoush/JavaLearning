package com.joush.day15.demo02BufferedStream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    java.io.BufferedOutputStream extends OutputStream
    BufferedOutputStream 字节输出缓冲流
    继承自父类的共性成员方法
        - public void close(); 关闭此输出流并释放与此流相关联任何系统资源
        - public void flush(); 刷新此输出流并强制任何缓冲的输出字节被写出
        - public void write(byte[] b); 将 b.length 字节从指定的字节数组写入值输出流
        - public void write(byte[] b, int off, int len); 将指定的字节数组写入 len 字节，从偏移量 off 开始输出到此输出流
        - public abstract void write(int b); 将指定的字节输出流
    构造方法
        - BufferedOutputStream(OutputStream out); 创建一个新的缓冲输出流，以将数据写入指定的底层输出流
        - BufferedOutputStream(OutputStream out, int size); 创建一个指定大小的缓冲输出流
        参数：
            OutputStream out，字节输出流，可以传递 FileOutputStream，会给 FileOutputStream 增加缓冲区，增加写入效率
            int size，指定缓冲流内部缓冲区的大小，不指定就是默认大小
    使用步骤
        1.创建 FileOutputStream 对象，构造方法中绑定要输出的目的地
        2.BufferedOutputStream 对象，构造方法传入 FileOutputStream对象，提高 FileOutputStream 对象效率
        3.使用 BufferedOutputStream 对象中的方法 write，把数据写入内部缓冲区
        4.使用 BufferedOutputStream 对象中的方法 flush，把缓冲区的数据刷新到文件中
        5.释放资源

 */
public class Demo01BufferedOutputStream {
    public static void main(String[] args) throws IOException {
        // 1.创建 FileOutputStream 对象，构造方法中绑定要输出的目的地
        FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo02BufferedStream\\prop.txt");

        // 2.BufferedOutputStream 对象，构造方法传入 FileOutputStream对象
        BufferedOutputStream buf = new BufferedOutputStream(fos);

        // 3.使用 BufferedOutputStream 对象中的方法 write，把数据写入内部缓冲区
        buf.write("写入数据".getBytes());

        // 4.flush，把缓冲区的数据刷新到文件中
        buf.flush();

        // 5.释放资源
        buf.close();
    }
}
