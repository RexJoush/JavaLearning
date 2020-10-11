package com.my.day18.demo08ReflectAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Pro(className = "com.my.day18.demo08ReflectAnnotation.Demo01",methodName = "show")
public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 1.解析注解，获取该类的字节码文件
        Class<ReflectTest> reflectTestClass = ReflectTest.class;

        // 2.获取注解对象
        Pro an = reflectTestClass.getAnnotation(Pro.class); // 其实相当于在内存中生成了一个该注解接口的实现对象

        // 3.调用注解对象定义的抽象方法，获取返回值
        String className = an.className();
        String methodName = an.methodName();

        // 4.加载该类进内存
        Class cls = Class.forName(className);

        // 5.创建对象
        Object obj = cls.newInstance();

        // 6.获取方法对象
        Method method = cls.getMethod(methodName);
        method.invoke(obj);
    }
}
