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
    <bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl"></bean>
    <bean id="accountDao" class="com.com.joush.dao.impl.AccountDaoImpl"></bean>

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

#### Spring 对 Bean 对象的管理细节

* 创建 bean 的三种方式
    - 使用默认构造函数创建
        ``` xml
        <!--  resource.bean.xml  -->
        <!--  在spring配置文件中使用 bean 标签，配以 id 和 class 属性后，且没有其他属性和标签时
        采用默认构造函数创建，如果没有默认构造函数，则无法创建  -->
        <bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl"></bean>
        ```
    - 使用普通工厂中的方法创建对象
        ``` xml
        <!--  resource.bean.xml  -->
        <!--  使用某个类中的方法创建对象，并存入 spring 容器  -->
        <bean id="instanceFactory" class="com.com.joush.factory.InstanceFactory"></bean>
        <bean id="accountService" factory-bean="instanceFactory" factory-method="getAccountService"></bean> -->
        ```
        ``` java
        // com.com.joush.factory.InstanceFactory.java
        public AccountService getAccountService(){
            return new AccountServiceImpl();
        }
        ```

    - 使用普通工厂中的静态方法创建对象
        ``` xml
        <!--  resource.bean.xml  -->
        <!--  使用某个类中的方法创建对象，并存入 spring 容器  -->
        <bean id="accountService" class="com.com.joush.factory.StaticFactory" factory-method="getAccountService"></bean>
        ```
        ``` java
        // com.com.joush.factory.StaticFactory.java
        
        public static AccountService getAccountService(){
            return new AccountServiceImpl();
        }
        ```
* bean 对象的作用范围
    - bean 标签的 scope 属性
    ``` xml
    <!--  resource.bean.xml  -->
    <!-- 
     bean 对象的作用范围
        作用，用于指定 bean 对象的作用范围
        取值，常用前两个
            singleton, 单例，默认值
            prototype, 多例
            request, 作用于 web 应用的请求范围
            session, 作用于 web 应用的会话范围
            global-session, 作用于集群环境的绘画范围（全局回话范围），当不是集群环境时，就是 session
     -->
    <bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl" scope="singleton"></bean> 
    ```
* bean 对象的生命周期
    - 单例对象
        - 出生，容器创建时出生
        - 存活，容器还在，对象一直存活
        - 死亡，容器销毁，对象消亡
    - 多例对象
        - 出生，使用对象时，spring 创建
        - 存活，对象的使用过程中，一直存活
        - 死亡，当对象长时间不用，且没有别的对象引用时，由 Java 的垃圾回收器回收

#### Spring 的依赖注入

* 概念
``` text
依赖注入，Dependency Injection
IOC 的作用，降低程序间的耦合
依赖关系的管理，以后都交给 spring 来维护
在当前类中需要用到其他类的对象，由spring 为我们提供，我们只需要在零配置文件中说明
依赖关系的维护，就称之为依赖注入
```
* 依赖注入
    - 能注入的数据，三类
        - 基本类型和 String
        - 其他 bean 类型，（在配置文件中或注解配置过的 bean）
        - 复杂类型/集合类型
    - 注入的方式，三种
        - 使用构造函数提供
        - 使用 set 方法提供
        - 使用注解提供
* 构造函数注入，使用标签 constructor-arg
``` xml
<!-- resources.bean.xml -->
在 bean 标签内部使用
标签的属性
    type, 用于指定要注入的数据类型，该类数据类型也是构造函数中某个或某些参数的类型
    index, 指定要注入的数据给构造函数中指定索引的位置，索引从 0 开始
    name, 用于给构造函数中指定名称的参数赋值

    - 以上用于给指定的构造函数中指定名称的参数赋值 -

    value, 用于给基本类型和 String 类型
    ref, 用于指定其他 bean 类型，它指的就是在 spring 的 ioc 核心容器中出现过的 bean 对象
优势
    在注入数据时，注入数据时必须的操作，否则无法创建对象成功
弊端
    改变了 bean 对象的实例化方式，使我们在创建对象时，用不到的数据也必须提供

<bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl">
    <constructor-arg type="java.lang.String" name="name" value="test"></constructor-arg>
    <constructor-arg type="int" name="age" value="18"></constructor-arg>
    <constructor-arg type="java.util.Date" name="birthday" ref="now"></constructor-arg>
</bean>
<bean id="now" class="java.util.Date"></bean>
```
* set 方法注入，使用 property 标签
``` xml
<!-- resources.bean.xml -->
在标签 bean 内部使用，同时需要提供 set 方法，且标签的 name 属性不与成员属性同名，而是与 set 方法同名
标签的属性
    name, 用于指定注入时所调用的 set 方法名称
    value, 用于给基本类型和 String 类型
    ref, 用于指定其他 bean 类型，它指的就是在 spring 的 ioc 核心容器中出现过的 bean 对象
优势
    创建对象是时，明确的限制，可以直接使用默认构造函数
 弊端
    如果某个成员必须有值，获取对象时 set 方法无法保证一定执行

<bean id="accountService2" class="com.com.joush.service.impl.AccountServiceImpl2">
    <property name="name" value="test2"></property>
    <property name="age" value="20"></property>
    <property name="birthday" ref="now"></property>
</bean>
```
* 注入集合数据 使用 list map array set props 标签
``` xml
<!-- resources.bean.xml -->
用于给 List 集合注入的标签
    list array set
用于给 Map 集合注入的标签
    map props
即，结构相同标签可以互换，只需使用记住两个即可

<bean id="accountService3" class="com.com.joush.service.impl.AccountServiceImpl3">
    <property name="myStrings">
        <array>
            <value>AAA</value>
            <value>BBB</value>
            <value>CCC</value>
        </array>
    </property>

    <property name="myList">
        <list>
            <value>AAA</value>
            <value>BBB</value>
            <value>CCC</value>
        </list>
    </property>

    <property name="mySet">
        <set>
            <value>AAA</value>
            <value>BBB</value>
            <value>CCC</value>
        </set>
    </property>

    <property name="myMap">
        <map>
            <!--  两种均可  -->
            <entry key="testA" value="aaa"></entry>
            <entry key="testB">
                <value>bbb</value>
            </entry>
        </map>
    </property>

    <property name="properties">
        <props>
            <prop key="testC">ccc</prop>
            <prop key="testD">ddd</prop>
        </props>
    </property>
</bean>
```

## Spring 基于注解的 IOC 配置

#### 基础概念
* 曾经的 xml 配置方法
``` xml
<bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl" 
    scope="" init-method="" destroy-method="" >
    
    <property name="" value="" ref=""></property>
</bean>
```
* 修改 xml 配置文件的约束，使得 spring 知道注解的位置
``` resource.bean.xml

<!--  注意，此时的 xml 头部信息和之前的 demo 已经不同了  -->

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!--  添加此标签告知 spring  -->
    <context:component-scan base-package="com.joush"></context:component-scan>

</beans>
```
* 注解的类型
    - 用于创建对象的
        - 作用和在 xml 配置中编写一个 <bean></bean> 标签实现功能一样的
        ``` java
        // com.joush.dao.impl.AccountServiceImpl.java
        /* 
            Component 注解
                作用：用于将本类对象存入 spring 容器中
                属性：value, 用于指定 bean 的 id 值，不写时，默认值为当前类名首字母改小写
            Controller 注解，一般用于表示层
            Service 注解，一般用于业务层
            Repository 注解，一般用于持久层
                作用：以上三个注解与 Component 一模一样，是 spring 框架为我们提供明确的三层使用的注解，是三层对象更清晰
        */
        @Component
        @Component("accountServiceImpl")
        @Component(value = "accountServiceImpl")
        public class AccountServiceImpl implements AccountService {
            // ...
        }
        ```
    - 用于注入数据的
        - 作用和在 xml 配置文件中的 bean 标签 写一个 <property></property> 的作用一样的
        ``` java
        // com.joush.service.impl.AccountServiceImpl.java
        /*
            Autowired 注解
            作用：自动按照类型注入，只要容器有唯一的一个 bean 对象类型和要注入的变量类型匹配，就可以注入成功
                  如果 ioc 容器中没有任何 bean 类型和要注入的变量类型匹配，则报错
                  如果 ioc 容器有多个匹配时，首先匹配类型，如果匹配到多个，则根据变量名进行匹配，如果匹配到唯一一个，则注入成功，如果匹配不到，则注入失败  
            出现位置：可以是成员变量，也可以是方法
            细节：使用注解注入时，set 方法不是必须的
            
            Quailfier 注解 
        */
        @Autowired
        private AccountDao accountDao = null;
        ```
    - 用于改变作用范围的
        作用和在 bean 标签中使用 scope 属性作用一样的
    - 和生命周期相关的
        作用和在 bean 标签中使用 init-method 和 destroy-method 属性作用一样的
