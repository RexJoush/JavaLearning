package com.project.Demo05TextSort;

import java.io.*;
import java.util.HashMap;

/*
    对文本内容进行排序

 */
public class Demo01Main {
    public static void main(String[] args) throws IOException {
        // 1.创建 HashMap 集合，存储行号和内容
        HashMap<String,String> map = new HashMap<>();
        // 2.创建字符缓冲输入流对象
        BufferedReader br = new BufferedReader(new FileReader("D:\\WorkSpace\\Java\\day01\\src\\com\\project\\Demo05TextSort\\in.txt"));
        // 3.创建字符缓冲输出流对象
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\project\\Demo05TextSort\\out.txt"));
        // 4.使用 readLine 读取文本
        String line = "";
        while ((line = br.readLine()) != null){
            // 5.对读取到的文本进行切割
            String[] arr =  line.split("\\.");
            // 6.切割好的放入 map 集合
            map.put(arr[0],arr[1]);
        }

        // 7.遍历 map 集合，写回文本，注意，HashMap 集合自动排序

        for (String s : map.keySet()){
            s += '.' + map.get(s) + "\r\n";
            bw.write(s);
        }
        // 释放资源
        bw.close();
        br.close();

    }
}
