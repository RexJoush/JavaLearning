package com.my.day13.demo04File;

import java.io.File;

/*
    路径
        绝对路径：是一个完整的路径
        相对路径
            相对当前文件的目录
    注意
        路径不区分大小写
 */
public class Demo02File {
    public static void main(String[] args) {
        /*
            File 类的构造方法
         */
        show03();
    }

    /*
        File(File parent, String child) 根据 parent 抽象路径和 child 路径组成新的 File 实例
            参数：把路径拆分成了两部分
            File parent 父路径
            String child 子路径
        好处
            父路径和子路径可以单独，字符路径都可以变化
            父路径是 File 类可以使用 File 类的方法对路径进行操作
     */
    private static void show03() {
        File parent = new File("C:\\");
        File file = new File(parent,"a.txt");
        System.out.println(file); // c:\a.txt
    }

    /*
        File(String parent, String child) 根据 parent 路径名字和 child 路径创建一个新的 File 实例
        参数：把路径拆分成了两部分
            String parent 父路径
            String child 子路径
        好处
            父路径和子路径可以单独，字符路径都可以变化
     */
    private static void show02(String parent, String child) {
        File f = new File(parent,child);
        System.out.println(f); // c:\a.txt
    }

    /*
        File(String pathname) 通过给定的路径字符串转换为抽象路径名来创建一个新的 File 实例
        参数：
            String pathname 字符串的路径名称
            路径可以是文件结尾，也可以是文件夹结尾
            路径也可以是相对路径，或者是绝对路径
            路径可以存在也可以不存在
     */
    private static void show01() {
        File f1 = new File("D:\\WorkSpace\\Java\\day01");
        System.out.println(f1); // D:\WorkSpace\Java\day01

        File f2 = new File("D:\\WorkSpace\\Java\\day01\\a.txt");
        System.out.println(f2); // D:\WorkSpace\Java\day01\a.txt

        File f3 = new File("b.txt");
        System.out.println(f3); // b.txt
    }
}
