package com.joush.ui;

import com.joush.service.AccountService;
import com.joush.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 模拟表现层用于调用业务层
 */
public class Client {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 1.获取核心容器对象
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.根据 id 获取 bean 对象
        AccountService accountService = (AccountService) ac.getBean("accountService");

        accountService.saveAccount();

        ac.close();

    }
}
