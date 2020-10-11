package com.project.Demo04FindFile;

import java.io.File;

/*
    找到某一目录下所有 .docx 的文件
    优化
        可以使用过滤器来优化
        在 File 类中，有两个和 listFiles 重载的方法，方法的参数传递的就是过滤器
        File[] listFiles(FileFilter filter);
            java.io.FileFilter 接口，用于抽象路径名 (File) 的过滤器
            作用：用来过滤文件
            抽象方法：用来过滤文件的方法
                boolean accept(File pathname); 测试指定抽象路径名是否应该包含在某个路径名列表中
                参数：
                    File pathname，使用 listFiles 方法遍历目录得到的文件对象
        File[] listFiles(FilenameFilter filter);
            java.io.FilenameFilter,实现此接口的类可以过滤文件名称
            作用：用来过滤文件名称
            抽象方法
                boolean accept(File dir,String name); 测试指定文件是否包含在某一文件列表中
                参数
                    File dir，构造方法中传递的被遍历目录
                    String name，使用 listFiles 方法遍历目录得到的文件对象
        注意：
            两个过滤器接口是没有实现类的，需要我们自己写实现类，重写过滤方法 accept，在方法中自定义过滤的规律
 */
public class Demo02FileFilter {
    public static void main(String[] args) {
        File f1 = new File("C:\\Users\\Joush\\Desktop");
        getAllFiles(f1);
    }

    // 定义一个方法
    public static void getAllFiles(File dir){
        File[] files = dir.listFiles(new FileFilterImpl()); // 穿传递过滤器
        for (File file : files) {
            if (file.isDirectory()){
                getAllFiles(file);
            }else {
                if (file.toString().endsWith(".docx")){
                    System.out.println(file);
                }
            }
        }
    }
}
