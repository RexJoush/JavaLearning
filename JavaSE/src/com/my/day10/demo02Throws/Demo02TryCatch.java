package com.my.day10.demo02Throws;

import java.io.IOException;

/*
    try...catch 异常处理的第二种方法，自己处理异常
    格式：
        try{
            // 可能产生异常的代码
        } catch (定义一个异常变量，用来接受异常){
            异常的处理逻辑，捕获异常对象后，怎么处理异常
            一般在工作中，会把异常信息记录到日志中
        } catch(异常类名 变量名){

        }
    注意事项：
        1.try 中可能会抛出多个异常对象那么就可以使用多个 catch 来处理这些异常对象。
        2.如果 try 中产生了异常就会执行 catch 中的异常处理逻辑，执行完毕 catch 中的语句
          继续执行 try...catch之后的代码，如果没有产生异常，会执行完 try 之后，执行之后的代码
 */
public class Demo02TryCatch {

    public static void main(String[] args) {
        try {
            readFile("D:\\a.tx");
        } catch (IOException e) { // try 中抛出什么异常对象，catch就定义什么异常变量，用来接受这个异常对象
            // e.printStackTrace();

            /*
                Throwable 类定义了3个异常处理的方法
                - String getMessage(); 返回此 throwable 的简短描述
                - String toString(); 返回此 throwable 的详细信息
                - void printStackTrace(); jvm 打印异常对象，默认调用此方法，最全的异常信息
             */
            System.out.println(e.getMessage());  // 文件的后缀名错误
            System.out.println(e.toString()); // java.io.IOException: 文件的后缀名错误; 重写 Object 类的 toString() 方法,
            e.getStackTrace(); // 最全面
        }
        System.out.println("后续代码");
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
