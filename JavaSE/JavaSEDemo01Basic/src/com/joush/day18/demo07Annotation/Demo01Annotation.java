package com.joush.day18.demo07Annotation;

/*
    jdk 中预定义的一些注解
        @Override，检测是否是重写父类方法
        @Deprecated，该注解表明的内容表示已过时，推荐使用新的
        @SuppressWarnings，压制警告
            一般传递参数 "all"
 */
@SuppressWarnings("all")
public class Demo01Annotation {

    @Override
    public String toString() {
        return super.toString();
    }

    @Deprecated
    public void show1(){
        // 有缺陷
    }


    public void show2(){

    }

    @Demo02MyAnnotation(show = "a",show2 = 1,age = 2)
    public void demo(){
        show1();
    }





}
