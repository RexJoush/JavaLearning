package com.my.day07.demo02MultiMethod;

/*
    在多态的代码中，成员方法的访问规则是
        看 new 的是谁，就优先用谁，没有则向上找
    成员方法，编译看左边，运行看右边
    成员变量，编译看左边，运行看左边
 */
public class Demo01MultiMethod {
    public static void main(String[] args) {
        Fu obj = new Zi();

        obj.method();  // 父子都有，优先用子
        obj.methodFu(); // 子类没有，向上找父

        // 编译看做，左边是 Fu，所以编译报错
        //obj.methodZi(); // 错误写法
    }
}
