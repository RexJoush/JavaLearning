package com.my.day07.demo07OuterClass;

/*
    如果一个类是定义在一个方法内部，那么这就是一个局部内部类，只有当前方法才能使用
    定义格式
    修饰符 class 类名称 {
        修饰符 返回值类型 外部类方法名称 (参数列表){
            class 内部类名称 {
                // ...
            }
        }
    }

    小结类的权限修饰符：
    public > protected > (default) > private
    定义一个类
    1.外部类 public / (default)
    2.成员内部类 public / protected / (default) / private
    3.局部内部类 什么都不能写
 */
public class Outer {

    public void methodOuter(){

        class Inner{ // 局部内部类
            int mun = 10;
            public void methodInner(){
                System.out.println(mun); // 10
            }
        }

        Inner inner = new Inner();
        inner.methodInner();
    }
}
