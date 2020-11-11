package com.joush.day15.demo04Encoding;

import java.io.FileReader;
import java.io.IOException;

public class Demo01FileReader {

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day15\\demo04Encoding\\GBK.txt");
        int len = 0;
        while ((len = fr.read()) != -1){
            System.out.println((char)len); //产生乱码
        }

        fr.close();
    }
}
