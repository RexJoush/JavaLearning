package com.joush.day16.demo01ObjectStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/*
    java.io.ObjectOutputStream extends OutputStream
    ObjectOutputStream，对象的序列化流
    作用：把对象以流的方式写入的文件中保存

    构造方法
        ObjectOutputStream(OutputStream out) 创建写入指定 OutputStream 的 ObjectOutputStream
        参数：OutputStream out，字节输出流
    特有的成员方法
        void writeObject(Object obj)将指定对象写入 ObjectOutputStream
    使用步骤：
        1.创建 ObjectOutputStream 对象，构造方法传递字节输出流
        2.使用 ObjectOutputStream 对象中的方法 writeObject 把对象写入到文件
        3.释放资源
 */
public class Demo01ObjectOutputStream {
    public static void main(String[] args) throws IOException {
        // 1.创建 ObjectOutputStream 对象，构造方法传递字节输出流
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day16\\demo01ObjectStream\\" +
                "b.txt"));

        // 2.使用 ObjectOutputStream 对象中的方法 writeObject 把对象写入到文件
        oos.writeObject(new Person("迪丽热巴",20)); // 抛出 java.io.NotSerializableException

        // 3.释放资源
        oos.close();

    }
}
