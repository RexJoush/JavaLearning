package com.joush.day13.demo04File;

import java.io.File;

/*
    File 类的判断方法和
        - public boolean exist() 判断 File 表示的文件或文件夹是否存在
        - public boolean isDirectory() 此 File 是否是目录
        - public boolean isFile() 此 File 是否是文件
 */

public class Demo04FileJudge {
    public static void main(String[] args) {

        show02();
    }

    /*
        public boolean isDirectory() 此 File 是否是目录
        public boolean isFile() 此 File 是否是文件
        注意：
            1.电脑中的文件只有两种，文件或文件夹
            2.这两个方法使用前提是文件或文件夹存在，如果不存在就返回 false
     */
    private static void show02() {
        File f1 = new File("a.txt");
        File f2 = new File("C:\\Users\\Joush\\Desktop");

        if (f2.exists()){
            System.out.println(f2.isDirectory()); // true
            System.out.println(f2.isFile()); // false
        }
    }

    /*
        public boolean exist() 判断 File 表示的文件或文件夹是否存在
     */
    private static void show01() {
        File f1 = new File("a.txt");
        System.out.println(f1.exists()); // false
        File f2 = new File("C:\\Users\\Joush\\Desktop\\李一航-个人简历.pdf");
        System.out.println(f2.exists()); // true
    }

}
