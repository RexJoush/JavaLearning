package com.my.day17.demo04Lambda;

/*
    使用 Lambda 特点优化日志案例
    Lambda 特点，延迟加载
    Lambda 使用前提，必须有函数式接口
 */
public class Demo02Lambda {
    // 定义一个显示日志的方法，参数传递日志的等级和 MessageBuilder接口
    public static void showLog(int level, MessageBuilder mb){
        // 对日志登记进行判断，1 级就调用 MessageBuilder 的 builderMessage 方法

        if (level == 1){
            System.out.println(mb.builderMessage());
        }

    }

    public static void main(String[] args) {
        String msg1 = "hello";
        String msg2 = "world";
        String msg3 = "java";

        /*
            此处使用 Lambda 表达式作为参数传递，仅仅把参数传递到 showLog 方法中，只有满足日记是一级，才会调用
            builderMessage 方法，然后才会拼接字符串，否则不会拼接，不会存在性能浪费
         */
        showLog(2,() -> msg1 + msg2 + msg3);
    }

}
