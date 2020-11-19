package com.joush.day18.demo08ReflectAnnotation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;

/*
    简单的测试框架
        当主方法执行后，会自动执行检测所有的方法（加了 check 注解的方法），判断是否有一场，记录到文件中
 */
public class TestCheck {
    public static void main(String[] args) throws IOException {
        // 1.创建计算器对象
        Calculator c = new Calculator();

        // 2.获取字节码文件
        Class<Calculator> calculatorClass = Calculator.class;

        // 3.获取所有方法
        Method[] methods = calculatorClass.getMethods();

        int num = 0; // 出现异常的次数
        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\WorkSpace\\Java\\day01\\src\\com\\my\\day18\\demo08ReflectAnnotation\\bug.txt"));

        for (Method method : methods) {
            // 4.判断方法是否有 Check 注解
            if (method.isAnnotationPresent(Check.class)){
                try {
                    // 5.有,执行
                    method.invoke(c);
                } catch (Exception e) {
                    // 6.捕获异常，记录到文件
                    num++;
                    bw.write( method.getName()+ " 方法出异常了 " + e.getClass().getSimpleName());
                    bw.newLine();
                    bw.write("异常的原因 " + e.getCause().getMessage());
                    bw.newLine();
                    bw.write("===============");
                    bw.newLine();
                }
            }
        }
        bw.write("本次测试一共出现 " + num + " 次异常");
        bw.flush();
        bw.close();
    }
}
