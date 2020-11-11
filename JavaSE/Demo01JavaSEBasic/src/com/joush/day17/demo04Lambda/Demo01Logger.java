package com.joush.day17.demo04Lambda;

/*
    日志案例
*/
public class Demo01Logger {
    // 根据日志的级别，显示日志信息
    public static void showLog(int level, String message){
        // 1级日志直接输出
        if (level == 1){
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        String msg1 = "hello";
        String msg2 = "world";
        String msg3 = "java";

        /*
            发现，以下代码存在性能浪费问题
            调用 showLog方法，传递的第二个参数是一个拼接的字符串
            如果等级不是 1 级，那么字符串不会输出，就白拼接了，存在浪费
         */
        // 调用 showLog 方法
        showLog(1,msg1 + msg2 + msg3); // hello world java



    }

}
