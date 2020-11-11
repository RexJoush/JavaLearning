package com.joush.day13.demo04File;

import java.io.File;

/*
    java.io.File 类
        文件和目录路径名的抽象表示形式
        java 把电脑中的文件和文件夹封装为一个 File 类，我们可以用 File 类对文件夹进行操作
        我们可以使用 File 类的方法
            创建/删除 一个文件或文件夹
            获取文件/文件夹
            判断文件/文件夹是否存在
            对文件/文件夹遍历
            获取文件大小
    File 类是一个与系统无关的类，任何操作系统都可以使用这个类的方法
    重点单词
        File 文件
        directory 文件夹/目录
        path 路径
 */
public class Demo01File {
    public static void main(String[] args) {
        /*
            static String pathSeparator 与系统有关的路径分隔符，为了方便，表示为字符串
            static char pathSeparatorChar 与系统有关的路径分隔符

            static String separator 与系统有关的默认名称分隔符，为了方便，表示为字符串
            static char SeparatorChar 与系统有关的默认名称分隔符

            操作例句不能写死
            C:\develop\a\a.txt windows
            C:/develop/a/a.txt Linux

            "C: + File.separator + "develop" + File.separator + "a" + File.separator + "a.txt"
         */

        String pathSeparator = File.pathSeparator;
        System.out.println(pathSeparator); // ; windows ; Linux :

        String separator = File.separator;
        System.out.println(separator);  // \ windows \ Linux /
    }
}
