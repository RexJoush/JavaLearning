package com.my.day13.demo04File;

import java.io.File;

/*
    File 类获取功能的方法
        - public String getAbsolutePath(); 返回此 File 对象的绝对路径
        - public String getPath(); 将此 File 转换为路径名字符串
        - public String getName(); 返回由此 File 表示的文件或目录的名称
        - public long length(); 返回文件的大小
 */
public class Demo03FileGet {
    public static void main(String[] args) {
        show04();
    }

    /*
        public long length(); 返回文件的大小
            获取构造方法指向文件的大小，以字节为单位
            注意：
                1.文件夹没有大小，返回 0
                2.如果构造方法的路径不存在，返回 0
     */
    private static void show04() {
        File f1 = new File("C:\\Users\\Joush\\Desktop\\李一航-个人简历.pdf");
        File f2 = new File("C:\\Users\\Joush\\Desktop\\a.pdf");
        System.out.println(f1.length()); // 103768
        System.out.println(f2.length()); // 0
    }

    /*
        public String getName(); 返回由此 File 表示的文件或目录的名称
            获取构造方法传递路径的结尾部分
     */
    private static void show03() {
        File f1 = new File("D:\\WorkSpace\\Java\\day01\\a.txt");
        File f2 = new File("D:\\WorkSpace\\Java\\day01");
        System.out.println(f1.getName()); // a.txt
        System.out.println(f2.getName()); // day01
    }

    /*
        public String getPath(); 将此 File 转换为路径名字符串
            获取构造方法传递的字符串
            toString 方法调用的就是 getPath 方法
     */
    private static void show02() {
        File f1 = new File("D:\\WorkSpace\\Java\\day01\\a.txt");
        File f2 = new File("b.txt");
        System.out.println(f1.getPath()); // D:\WorkSpace\Java\day01\a.txt
        System.out.println(f2.getPath()); // b.txt
    }

    /*
        public String getAbsolutePath(); 返回此 File 对象的绝对路径
        获取构造方法的传递的路径，无论相对还是绝对都返回绝对路径
     */
    private static void show01() {
        File f1 = new File("D:\\WorkSpace\\Java\\day01\\a.txt");
        String absolutePath1 = f1.getAbsolutePath();
        System.out.println(absolutePath1); // D:\WorkSpace\Java\day01\a.txt

        File f2 = new File("b.txt");
        String absolutePath2 = f2.getAbsolutePath();
        System.out.println(absolutePath2); // D:\WorkSpace\Java\day01\b.txt
    }
}
