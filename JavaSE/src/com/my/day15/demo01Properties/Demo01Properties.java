package com.my.day15.demo01Properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/*
    java.util.Properties extends Hashtable<k,v> implements Map<k,v>
    Properties 类表示了一个持久的属性集， Properties 可保存在流中或从流中加载
    Properties 集合是一个唯一和 IO 流相结合的集合
        可以使用 Properties 集合中的方法 store，把集合中的临时数据，持久化的写入硬盘
        可以使用 Properties 集合中的方法 load，把硬盘的键值对数据读取到集合
    属性列表中没一个键值对都是一个字符串
    Properties 集合是一个双列集合 key 和 value 默认都是字符串
 */
public class Demo01Properties {

    public static void main(String[] args) throws IOException {
        show03();
    }

    /*
        可以使用 Properties 集合中的方法 load，把硬盘的键值对数据读取到集合
        void load(InputStream inStream);
        void load(Reader reader);
        参数：
            InputStream inStream，字节输入流，不能读取含有中文的键值对
            Reader reader，字符输入流，可以读取含有中文的
     */
    private static void show03() throws IOException {
        Properties p = new Properties();
        FileReader fr = new FileReader("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo01Properties\\prop.txt");

        p.load(fr);

        for (String s : p.stringPropertyNames()){
            System.out.println(s + p.getProperty(s));
        }

    }

    /*
        可以使用 Properties 集合中的方法 store，把集合中的临时数据，持久化的写入硬盘
        void store(OutputStream out, String comments);
        void store(Writer writer, String comments);
        参数：
            OutputStream out，字节输出流，不能写中文
            Writer writer，字符输出流，可以写中文
            String comments，注释，用来解释说明保存的文件是做什么用的
                                  注意不能使用中文，一般使用空串
     */
    private static void show02() throws IOException {
        Properties p = new Properties();
        p.setProperty("赵丽颖","168");
        p.setProperty("迪丽热巴","165");
        p.setProperty("古力娜扎","160");

        FileWriter fw = new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo01Properties\\prop.txt",true);

        p.store(fw,"save");
        fw.close();

    }

    /*
        使用 Properties 集合存储数据，遍历集合取出数据
        Properties 集合操作字符串的方法
            Object setProperty(String key, String value)
            String getProperty(String key) 通过 key 找到 value，相当于 Map 集合的 get 方法
            Set<String> stringPropertyNames() 返回此集合中的键集， 相当于 Map 集合的 keySet 方法
        调用 Hashtable 的方法 put
     */
    private static void show01() {
        Properties p = new Properties();

        p.setProperty("赵丽颖","168");
        p.setProperty("迪丽热巴","165");
        p.setProperty("古力娜扎","160");

        Set<String> set = p.stringPropertyNames();
        for (String s : set){
            System.out.println(s + p.getProperty(s));
        }
    }
}
