package com.joush.ui;

import com.joush.dao.AccountDao;
import com.joush.service.AccountService;
import com.joush.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 模拟表现层用于调用业务层
 */
public class Client {

    /**
     * 获取 spring 的 ioc 核心容器并根据 id 获取对象
     *
     *  ApplicationContext 的三个常用实现类
     *      ClassPathXmlApplicationContext, 可以加在类路径下的配置文件，要求配置文件必须在类路径下
     *      FileSystemXmlApplicationContext, 可以加在磁盘任意路径下的配置文件，必须有访问权限
     *      AnnotationConfigApplicationContext, 用于读取注解创建容器的
     *
     *  核心容器所引发的两个问题：
     *      ApplicationContext  单例对象适用，实际开发用的更多
     *          它在构建核心容器时，创建对象采取的思想是立即加载的方式，读取完配置文件，则创建所有的对象
     *      BeanFactory 多例对象适用
     *          它在构建核心容器时，创建对象采用延迟加载的方式，当使用该对象时，才进行创建。
     * @param args
     */
    public static void main(String[] args) {

        // 1.获取核心容器对象
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.根据 id 获取 bean 对象
        AccountService accountService = (AccountService) ac.getBean("accountServiceImpl");

//        System.out.println(accountService);
//
//        AccountDao accountDao = (AccountDao) ac.getBean("accountDaoImpl");

        accountService.saveAccount();

        ac.close();


    }
}
