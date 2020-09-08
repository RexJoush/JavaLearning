package com.my.day14.demo06StreamException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
    jdk1.7的新特性
        在 try 的后边可以增加一个 ()，在括号中可以定义流对象
        那么这个流对象的作用域就在 try 中有效
        try 中的代码执行完毕，会自动把流对象关闭，不用写 finally
    格式：
        try(定义流对象){
            // xxx
        } catch (异常变量类型 变量名){
            // xxx
        }
 */
public class Demo02JDK7 {
    public static void main(String[] args) {
        // 创建文件读取流
        try(    FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo06StreamException\\a.txt");
                FileOutputStream fos = new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo06StreamException\\b.txt");
                ){
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

        //fos.close();
        //fis.close();
    }
}
