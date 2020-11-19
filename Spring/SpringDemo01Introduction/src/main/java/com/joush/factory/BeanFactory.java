package com.joush.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*
    一个创建 Bean 对象的工厂
    Bean
        - 在计算机英语中，有可重用组件的含义
    Java Bean，用 java 语言编写的可重用组件
        - 他就是创建我们的 service 和 dao 对象的

    1.需要一个配置文件来配置我们的 service 和 dao
            配置的内容，唯一标识 = 全类名
    2.通过读取配置文件中的内容，反射创建对象

    配置文件可以是 xml 也可以是 properties

 */
public class BeanFactory {

    // 定义一个 Properties 对象
    private static Properties properties;

    // 定义一个 map,用于存放要创建的对象，我们称之为容器
    private static Map<String, Object> beans;

    // 为 properties 对象赋值
    static {
        try {
            // 实例化对象
            properties = new Properties();
            // 获取 properties 文件的流对象
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            properties.load(in);

            // 实例化容器
            beans = new HashMap<>();

            // 取出配置文件中所有的 key
            Enumeration keys = properties.keys();

            // 遍历枚举
            while (keys.hasMoreElements()){
                // 取出 key
                String key = keys.nextElement().toString();

                // 根据 key 获取 value
                String beanPath = properties.getProperty(key);

                // 反射创建对象
                Object value = Class.forName(beanPath).getDeclaredConstructor().newInstance();

                // 把 key 和 value 存入 map 中
                beans.put(key, value);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化 properties 失败");
        }
    }

    /**
     * 根据 bean 的名称获取对象，单例化了
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        return beans.get(beanName);
    }

    /**
     * 根据 Bean 的名称获取 Bean 对象
     * @param beanName
     * @return
     */
//    public static Object getBean(String beanName){
//        Object bean = null;
//        try {
//            // 获取创建对象的全类名
//            String beanPath = properties.getProperty(beanName);
//
//            bean = Class.forName(beanPath).getDeclaredConstructor().newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return bean;
//    }

}
