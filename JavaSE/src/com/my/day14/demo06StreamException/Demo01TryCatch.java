package com.my.day14.demo06StreamException;

import java.io.FileWriter;
import java.io.IOException;

/*
    在 jdk1.7之前
    格式
        try{
            // xxx
        } catch (异常变量类型 变量名){
            // xxx
        } finally {
            // xxx
        }
 */
public class Demo01TryCatch {
    public static void main(String[] args) {
        // 变量在定义是可以没有值，但使用的时候必须有值
        FileWriter fw = null;
        try {
            fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day14\\demo06StreamException\\a.txt",true);
            for (int i = 0; i < 10; i++) {
                fw.write("HelloWorld" + i + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 如果创建对象失败，则会抛出 空指针异常，所以需要加 if 判断
            if (fw != null){
                try {
                    // close 声明抛出 IOException，所以必须处理
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
