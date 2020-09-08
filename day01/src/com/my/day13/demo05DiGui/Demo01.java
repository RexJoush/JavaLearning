package com.my.day13.demo05DiGui;

import java.io.File;

/*
    打印所有子目录和子文件夹
 */
public class Demo01 {
    public static void main(String[] args) {
        File f1 = new File("C:\\Users\\Joush\\Desktop");
        getAllFiles(f1);
    }

    // 定义一个方法
    public static void getAllFiles(File dir){
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()){
                getAllFiles(file);
            }else {
                System.out.println(file);
            }
        }
    }

}
