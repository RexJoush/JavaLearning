package com.joush;

/**
 * @author Rex Joush
 * @time 2022.03.11
 */
public class Test {

    public static void main(String[] args) {
        System.out.println("********扩展类加载器************");
        String property = System.getProperty("java.ext.dirs");
        System.out.println(property);
        Thread.currentThread();

    }

}
