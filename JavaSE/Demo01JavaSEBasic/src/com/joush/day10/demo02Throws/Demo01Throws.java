package com.joush.day10.demo02Throws;

import java.io.FileNotFoundException;
import java.io.IOException;

/*
    throws 关键字，异常处理的第一种方法，交给别人处理
    作用：当方法内部抛出异常对象的时候，那么我们就必须处理这个异常，可以使用 throws 关键字抛出给方法的调用者处理
    使用格式：在方法声明时使用
        修饰符 返回值类型 方法名(参数列表) throws AAAException, BBBException...{
            throw new AAAException("产生原因");
            throw new BBBException("产生原因");
        }
    注意：
        1.throws 必须写在方法声明处
        2.throws 后边声明的异常必须是 Exception 或者是 Exception的子类
        3.方法内部如果抛出多个异常，throws 后面必须也声明多个异常，如果多个异常有子父类关系，直接声明父类即可
        4.调用了一个声明异常的方法，就必须处理异常
            4.1 继续使用 throws 声明抛出，交给调用者处理
            4.2 或者 try...catch 自己处理异常
 */
public class Demo01Throws {

    /*
        FileNotFoundException extends IOException
        所以只需 throws 父类即可
     */
//    public static void main(String[] args) throws FileNotFoundException,IOException {
    public static void main(String[] args) throws IOException {
        readFile("C:\\a.txt");
    }

    /*
        定义一个方法，对参数传递的文件路径进行合法判断
        如果路径不是 "C:\\a.txt", 那么就抛出文件找不到异常
        注意：
            FileNotFoundException 是编译异常，所以抛出就必须处理
            可以 throws 继续抛出
     */
    public static void readFile(String fileName) throws FileNotFoundException,IOException{
        if (!fileName.equals("C:\\a.txt")){
            throw new FileNotFoundException("传递的路径不是C:\\a.txt");
        }

        /*
            如果文件的结尾不是 .txt 结尾，抛出 I/O异常
         */
        if (!fileName.endsWith(".txt")){
            throw new IOException("文件的后缀名错误");
        }


        System.out.println("读取文件");
    }
}
