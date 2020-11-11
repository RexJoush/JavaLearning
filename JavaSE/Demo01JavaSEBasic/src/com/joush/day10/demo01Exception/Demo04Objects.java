package com.joush.day10.demo01Exception;

import java.util.Objects;

/*
    Object 类中的静态方法
    public static <T> T requireNonNull(T obj); 查看指定的对象是不是空
    源码：
        public static <T> T requireNonNull(T obj){
            if (obj == null){
                throw new NullPointerException();
            return obj;
        }

 */
public class Demo04Objects {

    public static void main(String[] args) {
        method(null);
    }

    public static void method(Object object){

        // 对传递的参数判断是否合法，判断是否是 null
//        if (object == null){
//            throw new NullPointerException("参数为空");
//        }
        Objects.requireNonNull(object);
        Objects.requireNonNull(object,"参数为空");
    }

}
