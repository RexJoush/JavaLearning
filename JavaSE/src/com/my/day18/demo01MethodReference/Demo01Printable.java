package com.my.day18.demo01MethodReference;


public class Demo01Printable {
    // 定义一个方法，打印传递的字符串
    public static void printString(Printable p){
        p.print("helloworld");
    }

    public static void main(String[] args) {
        // 调用 printString 方法，方法的参数是一个函数式接口，所以可以传递 Lambda
        printString((s -> System.out.println(s)));

        /*
        分析：
            Lambda 表达式的目的，打印参数传递的字符串
            把参数 s,传递给了 System.out 对象，调用 out 对象的方法 println 对字符串进行输出
            注意
                1.System.out 对象是已经存在的
                2.println 方法也是存在的
            所以可以使用方法的引用了简化 Lambda 表达式
            可以使用 System.out 方法直接引用 println 方法
            :: 函数引用方法
        */
        printString(System.out::println);


    }

}
