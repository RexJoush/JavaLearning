package com.joush.day07.demo04Final;

/*
    当 final 用来修饰一个方法时，则该方法不能被重写
    格式:
        修饰符 final 返回值类型 方法名 (参数列表){
            // 方法体
        }
    注意事项：对于类，方法，来说 abstract 和 final 不能同时使用，因为矛盾
 */
public class Fu {
    public final void method(){
        System.out.println("父类方法执行");
    }
}
