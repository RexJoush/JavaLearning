## Spring 概述

#### 什么是 Spring
``` text
Spring是分层的Java SE/EE应用 full-stack 轻量级开源框架
以IoC（Inverse Of Control：反转控制）和AOP（Aspect Oriented Programming：面向切面编程）为内核
提供了展现层Spring MVC和持久层Spring JDBC以及业务层事务管理等众多的企业级应用技术.
```

#### Spring 的体系结构
* 程序的耦合
    - 耦合，简单理解为程序间的依赖关系
        - 类之间的依赖
        - 方法之间的依赖
    - 解耦，降低程序间的依赖关系
    - 实际开发中应做到，编译期不依赖，运行期依赖
* 解耦的思路
    - 使用反射来创建对象，避免使用 new 关键字
    - 通过读取配置文件来获取要创建的对象全类名

#### 解耦
* Bean
    - 在计算机英语中，有可重用组件的含义
* Java Bean，用 java 语言编写的可重用组件
    - 他就是创建我们的 service 和 dao 对象的

## IOC (Inverse Of Control, 控制反转)
``` text
把创建对象的权利交给框架，是框架的重要特征，并非面向对象编程的专用术语
它包括依赖注入（Dependency Injection）和依赖查找（Dependency Lookup）。

明确 ioc 的作用，降低程序间的耦合性
```
#### Spring 使用 IoC
* 添加 spring 依赖
``` xml
<!--  pom.xml  -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.11.RELEASE</version>
</dependency>
```
* 添加 bean.xml 配置
``` xml
<!--  resources.bean.xml  -->

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!--  把对象的创建交给 spring 管理  -->
    <bean id="accountService" class="com.joush.service.impl.AccountServiceImpl"></bean>
    <bean id="accountDao" class="com.joush.dao.impl.AccountDaoImpl"></bean>

</beans>
```
* 测试方法
``` java
// com.joush.ui.Client.java

// 1.获取核心容器对象
/*
    ApplicationContext 的三个常用实现类
        ClassPathXmlApplicationContext, 可以加在类路径下的配置文件，要求配置文件必须在类路径下
        FileSystemXmlApplicationContext, 可以加在磁盘任意路径下的配置文件，必须有访问权限
        AnnotationConfigApplicationContext, 用于读取注解创建容器的
*/
ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
// ApplicationContext ac = new FileSystemXmlApplicationContext("E:\\WorkSpace\\JavaLearning\\Spring\\Demo02SpringIoC\\src\\main\\resources\\bean.xml");
// 2.根据 id 获取 bean 对象
AccountService accountService = (AccountService) ac.getBean("accountService");
// AccountDao accountDao = ac.getBean("accountDao", AccountDao.class); // 两种方式均可

accountService.saveAccount();
```
* 核心容器所引发的两个问题：
    - ApplicationContext  单例对象适用，实际开发用的更多
        - 它在构建核心容器时，创建对象采取的思想是立即加载的方式，读取完配置文件，则创建所有的对象
    - BeanFactory 多例对象适用
        - 它在构建核心容器时，创建对象采用延迟加载的方式，当使用该对象时，才进行创建。
