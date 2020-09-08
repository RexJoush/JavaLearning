package com.my.day02.demo03Static;

/*
    一旦使用 static 修饰成员方法，那么方法就成为静态方法，静态方法不属于对象，属于类

    如果没有 static 关键字，必须首先创建对象才能调用方法
    如果有 static 关键字，可以通过类名直接调用方法

    无论是成员变量还是成员方法，有了 static 关键字，都可以使用类名调用
    注意事项：
    静态不能直接访问非静态
 */
public class Demo02StaticMethod {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.method();
        // 对于静态方法可以使用类名直接调用
        obj.methodStatic(); // 正确，但不推荐，编译后也会变成类名直接调用
        MyClass.methodStatic(); // 正确，推荐
    }
}
