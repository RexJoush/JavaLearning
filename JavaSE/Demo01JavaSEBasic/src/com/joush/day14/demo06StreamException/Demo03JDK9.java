package com.joush.day14.demo06StreamException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    jdk9 的新特性
    try 的前面可以定义流对象
    在 try 的后面 () 可以直接引入流对象名称
    在 try 代码执行完毕后，流对象也可以释放掉，不用写 finally
    格式：
        A a = new A();
        B b = new B();
        try(a;b){
            // xxx
        } catch (异常变量类型 变量名){
            // xxx
        }
 */
public class Demo03JDK9 {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo06StreamException\\a.txt");
        FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo06StreamException\\b.txt");
        try(fis;fos){
            int len = 0;
            byte[] bytes = new byte[1024];
            // 读取文件
            while ((len = fis.read(bytes)) != -1){
                System.out.println(new String(bytes,0,len));
                fos.write(bytes,0,len);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
