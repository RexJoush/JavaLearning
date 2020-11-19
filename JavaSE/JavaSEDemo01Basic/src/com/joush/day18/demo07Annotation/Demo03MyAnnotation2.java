package com.joush.day18.demo07Annotation;

/*
    元注解
            public @interface 注解名称{
                属性列表;
            }
        元注解，用于描述注解的注解
            @Target，描述注解能够作用的位置
                ElementType.TYPE，可以作用于类上
                ElementType.METHOD，可以作用于方法上
                ElementType.FIELD，可以作用于成员变量上
            @Retention，描述注解的保留阶段
                RetentionPolicy.RUNTIME，运行阶段
                RetentionPolicy.CLASS，编译阶段
                RetentionPolicy.SOURCE，源码阶段
            @Documented，描述注解是否被抽取到 api 文档中
            @Inherited，描述注解是否被子类继承
 */

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE,ElementType.METHOD}) //表示只能作用于类上
@Retention(RetentionPolicy.RUNTIME) // 表示保留到运行阶段，即在运行时 jvm 可以读取到这个注解
@Documented // 描述注解是否会被提取到 api 文档中
@Inherited // 注解是否会被子类继承
public @interface Demo03MyAnnotation2 {
}
