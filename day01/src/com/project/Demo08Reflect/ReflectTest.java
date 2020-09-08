package com.project.Demo08Reflect;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/*
    框架类
 */
public class ReflectTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        /*
            可以创建任意类的对象，可以执行任意方法,不能改变该类的代码，可以创建任意类的对象，可以执行任意方法
         */

        // 1.加载配置文件
        // 1.1 创建 Properties 对象
        Properties properties = new Properties();
        // 1.2 加载配置文件，转换为集合
            // 1.2.1 获取目录下的配置文件
            ClassLoader classLoader = ReflectTest.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("pro.properties");
            properties.load(is);
        // 2.获取配置文件中定义的数据
        String className = properties.getProperty("className");
        String methodName = properties.getProperty("methodName");

        // 3.加载该类进内存
        Class cls = Class.forName(className);

        // 4.创建对象
        Object obj = cls.newInstance();

        // 获取方法对象
        Method method = cls.getMethod(methodName);
        method.invoke(obj);

    }
}
