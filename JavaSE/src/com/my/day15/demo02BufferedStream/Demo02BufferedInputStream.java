package com.my.day15.demo02BufferedStream;


import java.io.*;

/*
    java.io.BufferedInputStream extends InputStream
    BufferedInputStream 字符输入流
    继承自父类的共性成员方法
        - int read() 从输入流中读取数据的下一个字节
        - int read(byte[] b) 从输入流中读取一定数量的字节，并将其存储在缓冲区数组 b 中
        - void close() 关闭输入流并释放与该流关联的所有系统资源
    构造方法
        - BufferedInputStream(InputStream out); 创建一个新的缓冲输入流，以将数据写入指定的底层输入流
        - BufferedInputStream(InputStream out, int size); 创建一个指定大小的缓冲输入流
        参数：
            InputStream out，字节输入流，可以传递 FileInputStream，会给 FileInputStream 增加缓冲区，增加写入效率
            int size，指定缓冲流内部缓冲区的大小，不指定就是默认大小
    使用步骤
        1.创建 FileInputStream 对象，构造方法中绑定要读取的目的地
        2.BufferedInputStream 对象，构造方法传入 FileInputStream对象，提高 FileInputStream 对象效率
        3.使用 BufferedInputStream 对象中的方法 write，把数据写入内部缓冲区
        4.使用 BufferedInputStream 对象中的方法 flush，把缓冲区的数据刷新到文件中
        5.释放资源

 */
public class Demo02BufferedInputStream {
    public static void main(String[] args) throws IOException {
        // 1.创建 FileOutputStream 对象，构造方法中绑定要输出的目的地
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo02BufferedStream\\prop.txt");

        // 2.BufferedInputStream 对象，构造方法传入 FileInputStream对象
        BufferedInputStream bis = new BufferedInputStream(fis);

        // 3.使用 BufferedInputStream 对象中的方法 read，读取数据
        /*
        int len = 0;
        while ((len = bis.read()) != -1){
            System.out.println(len);
        }

         */

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = bis.read(bytes)) != -1){
            System.out.println(new String(bytes,0,len));
        }

        // 4.释放资源
        bis.close();
    }
}
