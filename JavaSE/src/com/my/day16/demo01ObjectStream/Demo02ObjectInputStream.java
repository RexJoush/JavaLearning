package com.my.day16.demo01ObjectStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/*
    java.io.ObjectInputStream extends InputStream
    ObjectInputStream，对象的反序列化流
    作用：把文件中保存的对象，以流的方式读取出来使用

    构造方法：
        ObjectInputStream(InputStream out) 创建从 InputStream 读取的 ObjectInputStream
        参数
            InputStream in，字节输入流
    特有的成员方法
        Object readObject(); 从 ObjectInputStream 读取对象

    注意事项
        readObject 方法声明抛出了 ClassNotFoundException，class文件找不到异常
        当不存在对象的 class 文件时，抛出此异常
        反序列化的前提
            1.类必须实现 Serializable 接口
            2.必须存在类对应的 class 文件

 */
public class Demo02ObjectInputStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1.创建一个 ObjectInputStream 对象，构造方法传递字节输入流
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day16\\demo01ObjectStream\\" +
                "b.txt"));

        // 2.使用 ObjectInputStream 对象的方法 readObject 读取保存对象的文件
        Object object = ois.readObject();

        // 3.释放资源
        ois.close();

        // 4.使用读取出来的对象
        System.out.println(object);
    }
}
