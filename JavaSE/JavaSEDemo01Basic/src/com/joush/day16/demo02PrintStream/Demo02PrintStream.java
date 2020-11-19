package com.joush.day16.demo02PrintStream;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/*
    可以改变输出语句的目的地（打印流的流向）
    输出语句默认控制台输出
    使用 System.setOut方法将输出语句的目的地改为参数传递的目的地
        static void setOut(PrintStream out); 重新分配标准输出流
 */
public class Demo02PrintStream {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("输出在控制台");

        PrintStream ps = new PrintStream("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day16\\demo02PrintStream\\a.txt");
        System.setOut(ps);

        System.out.println("输出在 ps 中");

        ps.close();

    }
}
