package com.my.day18.demo06Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
    2.获取构造方法
        Constructor<?>[] getConstructors();
        Constructor<?> getConstructor(类<?>... parameterTypes);

        Constructor<?>[] getDeclaredConstructors();
        Constructor<?> getDeclaredConstructor(类<?>... parameterTypes);
 */
public class Demo03ReflectConstructor {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class personClass = Person.class;

        // Constructor<?> getConstructor(类<?>... parameterTypes);
        Constructor constructor = personClass.getConstructor(String.class, int.class);
        Object instance = constructor.newInstance("张三",20);

        System.out.println(instance); // Person{name='张三', age=20}

        // 1.空参
        Constructor constructor1 = personClass.getConstructor();
        Object instance1 = constructor1.newInstance();

        System.out.println(instance1); // Person{name='null', age=0}
        // 2.空参
        Object o = constructor1.newInstance();
        System.out.println(o); // Person{name='null', age=0}


        // constructor.setAccessible(true); // 暴力反射

    }
}
