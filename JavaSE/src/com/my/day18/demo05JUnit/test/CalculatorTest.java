package com.my.day18.demo05JUnit.test;

import com.my.day18.demo05JUnit.junit.Calculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

    /**
     * 初始化方法
     * 用于资源申请，所有测试方式执行之前都执行该方法
     * 加上 Before 注解
     */
    @Before
    public void init(){
        System.out.println("init...");
    }

    /**
     *  释放资源的方法
     *  在所有测试方法执行完之后，都会自动执行该方法
     *  加上 After 注解
     */
    @After
    public void close(){
        System.out.println("close...");
    }

    /**
     * 测试 add 方法
     */
    @Test
    public void testAdd(){
        System.out.println("running");
        // 创建计算器对象，调用 add 方法
        Calculator c= new Calculator();
        // int i = 3/0;
        int result = c.add(1, 2);

        // 3.断言，断言结果是多少，然后结果会进行比对，成功则为绿色，否则为红色
        Assert.assertEquals(3,result);

    }

    @Test
    public void testSub(){
        Calculator c= new Calculator();
        int result = c.sub(1,2);
        Assert.assertEquals(-1,result);
    }

}
