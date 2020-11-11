package com.joush.day18.demo07Annotation;

/*
    自定义注解
        - 格式
            元注解
                public @interface 注解名称{
                    属性列表;
                }
            元注解，用于描述注解的注解
                @Target，描述注解能够作用的位置
                @Retention，描述注解的保留阶段
                @Documented，描述注解是否被抽取到 api 文档中
                @Inherited，描述注解是否被子类继承
        - 本质
            注解本质就是一个接口，该接口默认继承 Annotation 接口
                public interface MyAnnotation extends java.lang.annotation.Annotation {}
        - 属性
            即接口中可以定义的抽象成员方法
            要求
                1.属性的返回值类型
                    - 基本数据类型
                    - String
                    - 枚举
                    - 注解
                    - 以上类型的数组
                2.定义了属性，在使用是需要给属性赋值
                    1.如果定义属性时使用 default 关键字默认初始化，那么再在使用注解时可以不进行赋值
                    2.如果只有一个属性需要赋值，并且赋值名称是 value，则 value 可以省略，直接定义值即可
                    3.数组赋值时，使用 {} 包裹，如果数组只有一个值 {} 可以省略

 */
public @interface Demo02MyAnnotation {

    String show();
    int show2();
    int age();

    double oc() default 3.4;

}
