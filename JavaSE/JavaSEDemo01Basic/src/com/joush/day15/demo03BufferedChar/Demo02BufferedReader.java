package com.joush.day15.demo03BufferedChar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/*
    java.io.BufferedReader extends Reader
    BufferedReader 字符缓冲输入流
    继承自父类的共性成员方法
        - int read(); 读取单个字符并返回
        - int read(char[] cbuf); 一次读取多个字符，将字符读入数组
        - void close(); 关闭并释放资源
    构造方法：
        BufferedReader(Reader in)，创建一个使用默认大小输出缓冲区的字符输入流
        BufferedReader(Reader in，int size)，创建一个指定大小输出缓冲区的字符输入流
    特有的成员方法
        void readLine() 读取一个文本行，读取一行数据
            行的终止符号是换行符
            返回值，包含该行内容的字符串，不包含终止符，如果已达末尾，则返回 null
 */
public class Demo02BufferedReader {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo03BufferedChar\\prop.txt");
        BufferedReader br = new BufferedReader(fr);

        /*
        String line = br.readLine();
        System.out.println(line);
         */

        // 循环优化
        String line = "";
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }

        br.close();
    }
}
