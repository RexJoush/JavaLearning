package com.joush.day07.demo06InnerClass;

/*
    如果出现重名现象
    外部类名称.this.外部类变量名
 */
public class Outer {

    int num = 10; // 外部类成员变量

    public class Inner {
        int num = 20; // 内部类成员变量

        public void methodInner(){
            int num = 30; // 内部类成员方法的局部变量
            System.out.println(num); // 30
            System.out.println(this.num); // 20
            System.out.println(Outer.this.num); // 10
        }

    }
}
