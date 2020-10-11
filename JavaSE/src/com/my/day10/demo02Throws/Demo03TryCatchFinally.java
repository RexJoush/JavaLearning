package com.my.day10.demo02Throws;

import java.io.IOException;

/*
    finally 代码块
    格式：
        try{
            // 可能产生异常的代码
        } catch (定义一个异常变量，用来接受异常){
            异常的处理逻辑，捕获异常对象后，怎么处理异常
            一般在工作中，会把异常信息记录到日志中
        } catch(异常类名 变量名){

        } finally {
            // 无论是否出现异常，此处代码都会执行
        }
    注意：
        1.finally 不能单独使用，必须和 try 一起使用
        2.finally 一般用于资源释放
 */
public class Demo03TryCatchFinally {

    public static void main(String[] args) {
        try {
            // 可能产生异常的代码
            readFile("C:\\a.tx");
        } catch (IOException e) {
            // 异常处理
            e.printStackTrace(); // java.io.IOException: 文件的后缀名错误
                                //      at com.my.day10.demo02.Demo03TryCatchFinally.readFile(Demo03TryCatchFinally.java:48)
                                //      at com.my.day10.demo02.Demo03TryCatchFinally.main(Demo03TryCatchFinally.java:27)
        } finally {
            System.out.println("finally代码"); // finally代码
        }



    }

    public static void readFile(String fileName) throws IOException {
//        if (!fileName.equals("C:\\a.txt")){
//            throw new FileNotFoundException("传递的路径不是C:\\a.txt");
//        }

        /*
            如果文件的结尾不是 .txt 结尾，抛出 I/O异常
         */
        if (!fileName.endsWith(".txt")){
            throw new IOException("文件的后缀名错误");
        }


        System.out.println("读取文件");
    }
}
