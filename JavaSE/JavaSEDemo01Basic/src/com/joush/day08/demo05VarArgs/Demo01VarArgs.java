package com.joush.day08.demo05VarArgs;

/*
    可变参数，jdk1.5+ 之后出现的新特性
    使用前提，当方法的参数列表数据类型已经确定，但个数不确定，就可以使用可变参数
    使用格式，定义方法时使用
        修饰符 返回值类型 方法名(数值类型... 变量名){}
    可变参数的原理，底层就是一个数组，根据传递参数不同，会创建不同长度的数组，来存储这些参数
    传递的参数可以是 0个，或者 1个或多个



 */
public class Demo01VarArgs {
    public static void main(String[] args) {
        int i = add(10,20,30,40,50,60);
    }

    // 计算多个整数的和
    public static int add(int...arr){
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return 0;
    }

    /*
        可变参数注意事项
            1.一个方法的参数列表，只能有一个可变参数
            2.如果方法有多个参数，那么可变参数必须写在参数列表的末尾
     */
    public static void method1(String a,double b,int c,int...arr){

    }

    /*
        可变参数的终极写法
     */
    public static void method2(Object...obj){

    }

}
