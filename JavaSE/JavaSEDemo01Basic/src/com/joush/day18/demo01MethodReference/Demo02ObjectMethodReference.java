package com.joush.day18.demo01MethodReference;

/*
    通过对象名引用成员方法
    使用前提是对象名已经存在的，成员方法也是已经存在
    就可以使用对象名来引用成员方法
 */
public class Demo02ObjectMethodReference {
    // 定义一个成员方法，方法传递 Printable 接口
    public static void printString(Printable p){
        p.print("HelloWorld");
    }

    public static void main(String[] args) {
        // 调用 printString 方法，方法的参数是一个函数式接口，所以可以传递 Lambda 表达式
        printString((s)->{
            ObjectMethodReference obj = new ObjectMethodReference();
            // 调用 ObjectMethodReference 中的方法 printUpperCaseString
            obj.printUpperCaseString(s);
        });

        /*
            使用方法引用优化 Lambda 表达式
                1.对象已经存在，ObjectMethodReference
                2.成员方法也已经存在 printUpperCaseString
            所以可以使用对象名来引用成员方法
         */
        ObjectMethodReference obj = new ObjectMethodReference();
        printString(obj::printUpperCaseString);

    }

}
