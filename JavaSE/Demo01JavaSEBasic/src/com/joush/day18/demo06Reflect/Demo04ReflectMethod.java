package com.joush.day18.demo06Reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
    3.获取成员方法
        Method[] getMethods();
        Method getMethod(String name, 类<?>... parameterTypes)

        Method[] getDeclaredMethods();
        Method getDeclaredMethod(String name, 类<?>... parameterTypes)
    4.获取类名
        String getName();
 */
public class Demo04ReflectMethod {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class personClass = Person.class;

        // 获取指定名称的方法，空参
        Method eat = personClass.getMethod("eat");
        Person p = new Person();

        // 执行方法
        eat.invoke(p);

        // 获取指定名称的方法，带参
        Method method = personClass.getMethod("eat", String.class);
        method.invoke(p,"food");


        // 获取所有 public 方法
        Method[] methods = personClass.getMethods();
        for (Method method1 : methods) {
            System.out.println(method1);
            System.out.println(method1.getName());
        }

        // 获取类名
        String className = personClass.getName();
        System.out.println(className); // com.my.day18.demo06Reflect.Person

    }
}
