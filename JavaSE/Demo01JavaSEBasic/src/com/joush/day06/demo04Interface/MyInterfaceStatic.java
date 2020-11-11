package com.joush.day06.demo04Interface;

/*
    从 java8 开始，接口当中允许定义静态方法
    格式
        public static 返回值类型 方法名称 (参数列表){
            方法体
        }
    提示，就是不死 abstract 或者 default 换成 static 即可
 */

public interface MyInterfaceStatic {
    public static void methodStatic(){
        System.out.println("这是接口的静态方法! ");
    }
}
