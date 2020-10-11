package com.my.day13.demo04File;

import java.io.File;
import java.io.IOException;

/*
    File 类创建删除的方法
        - public boolean createNewFile(); 当且仅当具有该名称的文件不存在时，创建一个新的空文件
        - public boolean delete(); 删除由此 File 表示的文件或目录
        - public boolean mkdir(); 创建由此 File 表示的目录
        - public boolean mkdirs(); 创建由此 File 表示的目录，包括父目录
 */
public class Demo05FileCreatDelete {
    public static void main(String[] args) throws IOException {
        show03();
    }

    /*
        public boolean delete(); 删除由此 File 表示的文件或目录
        删除构造方法中的文件和文件夹
        返回值
            true，文件夹/文件删除成功
            false，文件夹/文件删除失败，或者构造方法中路径不存在
        注意
            delete 是直接在硬盘上删除文件，不经过回收站，删除要谨慎

     */
    private static void show03() {
        File f1 = new File("\\abc");
        System.out.println(f1.delete()); // false
    }

    /*
        - public boolean mkdir(); 创建由此 File 表示的目录,只可创建单级文件夹
        - public boolean mkdirs(); 创建由此 File 表示的目录，包括父目录，即多级文件夹
        创建文件夹的位置和名称，由构造方法给出
        返回值
            true，文件夹不存在，创建成功
            false，文件夹存在，创建失败
        1.此方法只能创建文件夹，不能创建文件
        2.创建文件的路径必须存在，否则抛出异常
     */
    private static void show02() {
        File f1 = new File("\\abc");
        System.out.println(f1.mkdir()); // false
        File f2 = new File("\\abc\\aa\\cc\\bb");
        System.out.println(f2.mkdirs()); // true,路径不存在不创建，也不会抛异常
    }

    /*
        public boolean createNewFile(); 当且仅当具有该名称的文件不存在时，创建一个新的空文件
        创建文件的位置和名称，由构造方法给出
        返回值
            true，文件不存在，创建成功
            false，文件存在，创建失败
        1.此方法只能创建文件，不能创建文件夹
        2.创建文件的路径必须存在，否则抛出异常
     */
    private static void show01() throws IOException {
        File f1 = new File("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day13\\demo04File\\1.txt");
        System.out.println(f1.createNewFile());

        File f2 = new File("2.txt");
        System.out.println(f2.createNewFile());

        // 注意，创建的是文件还是文件夹不要看名字，要看方法
        File f3 = new File("\\aaa");
        System.out.println(f3.createNewFile());
    }
}
