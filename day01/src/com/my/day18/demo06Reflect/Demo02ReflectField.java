package com.my.day18.demo06Reflect;

import java.lang.reflect.Field;

/*
    1.获取成员变量
        Filed[] getFields();
        Filed getField(String name);

        Filed[] getDeclaredFields();
        Filed getDeclaredField(String name);
 */
public class Demo02ReflectField {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 1.获取类的 Class 对象
        Class personClass = Person.class;

        // 获取成员变量
        Field[] fields = personClass.getFields();
        for (Field field : fields){
            System.out.println(field); // public int a; 只能所有获取公有成员变量
        }
        System.out.println("=========");

        Field a = personClass.getField("a");
        // 获取成员变量 a 的值
        Person p = new Person();
        Object o = a.get(p);
        System.out.println(o); // 0

        // 设置 a
        a.set(p,"张三");
        System.out.println(p.a); // 张三

        /*
            Filed[] getDeclaredFields(); 获取所有成员变量，不考虑修饰符
         */
        Field[] declaredFields = personClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }

        Field d = personClass.getDeclaredField("d");

        // 在使用非 public 修饰符的成员时，需要忽略安全检查
        d.setAccessible(true); // 暴力反射
        Object o1 = d.get(p);
        System.out.println(o1); // 抛出异常 java.lang.IllegalAccessException
    }
}
