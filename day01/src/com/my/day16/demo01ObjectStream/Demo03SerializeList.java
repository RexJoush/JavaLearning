package com.my.day16.demo01ObjectStream;

import java.io.*;
import java.util.ArrayList;

/*
    练习：序列化集合
        当我们像在文件中保存多个对象的时候，可以把多个对象存储到集合中，对集合进行序列化

 */
public class Demo03SerializeList {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1.定义一个存储 Person 对象的 ArrayList 集合
        ArrayList<Person> list = new ArrayList<>();

        // 2.往 ArrayList 集合中存储数据
        list.add(new Person("迪丽热巴",29));
        list.add(new Person("古力娜扎",28));
        list.add(new Person("玛尔扎哈",19));

        // 3.创建一个序列化流 ObjectOutputStream 对象
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day16\\demo01ObjectStream\\b.txt"));

        // 4.使用 ObjectOutputStream 对象的方法 writeObject 写入
        oos.writeObject(list);

        // 5.创建反序列化 ObjectInputStream 对象，使用 readObject 方法读出内容
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day16\\demo01ObjectStream\\b.txt"));
        Object os = ois.readObject();

        // 6.将 Object 的结果转成 ArrayList 集合，并遍历
        for (Person p : (ArrayList<Person>) os){
            System.out.println(p.getName() + "," + p.getAge());
        }

        // 7.关闭资源
        ois.close();
        oos.close();

    }
}
