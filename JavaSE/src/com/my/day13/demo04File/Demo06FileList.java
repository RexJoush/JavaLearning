package com.my.day13.demo04File;

import java.io.File;

/*
    File 类的遍历方法
        - public String[] list(); 返回 String[]，表示该 File 目录中的所有子文件夹或目录
        - public File[] listFiles(); 返回一个 File[]，表示该 File 目录中的所有子文件夹或目录
    注意
        1.list 和 listFiles 方法遍历的是构造方法中给出的目录
        2.如果方法给出的目录不存在，就抛出空指针异常
        3.如果给出的不是目录，也会抛出空指针异常
 */
public class Demo06FileList {
    public static void main(String[] args) {
        show02();
    }

    /*
        public File[] listFiles(); 返回一个 File[]，表示该 File 目录中的所有子文件夹或目录
     */
    private static void show02() {
        File f1 = new File("C:\\Users\\Joush\\Desktop");
        for (File f : f1.listFiles()){
            System.out.println(f.getName());
        }
    }

    /*
        public String[] list(); 返回 String[]，表示该 File 目录中的所有子文件夹或目录
     */
    private static void show01() {
        File f1 = new File("C:\\Users\\Joush\\Desktop");
        for (String s : f1.list()){
            System.out.println(s);
        }
    }
}
