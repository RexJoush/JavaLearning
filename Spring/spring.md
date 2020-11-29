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
<!-- 在 bean 标签内部使用
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
    改变了 bean 对象的实例化方式，使我们在创建对象时，用不到的数据也必须提供 -->

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
<!-- 在标签 bean 内部使用，同时需要提供 set 方法，且标签的 name 属性不与成员属性同名，而是与 set 方法同名
标签的属性
    name, 用于指定注入时所调用的 set 方法名称
    value, 用于给基本类型和 String 类型
    ref, 用于指定其他 bean 类型，它指的就是在 spring 的 ioc 核心容器中出现过的 bean 对象
优势
    创建对象是时，明确的限制，可以直接使用默认构造函数
 弊端
    如果某个成员必须有值，获取对象时 set 方法无法保证一定执行 -->

<bean id="accountService2" class="com.com.joush.service.impl.AccountServiceImpl2">
    <property name="name" value="test2"></property>
    <property name="age" value="20"></property>
    <property name="birthday" ref="now"></property>
</bean>
```
* 注入集合数据 使用 list map array set props 标签
``` xml
<!-- resources.bean.xml -->
<!-- 用于给 List 集合注入的标签
    list array set
用于给 Map 集合注入的标签
    map props
即，结构相同标签可以互换，只需使用记住两个即可 -->

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
``` xml

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
#### 注解的类型
* 用于创建对象的
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
* 用于注入数据的
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
        作用：再按照类型的基础之上，在按照名称注入。在给类成员注入时不能单独使用，需和 Autowired 注解一起使用但在给方法参数注入时，可以使用
        属性：value 用于指定注入 bean 的 id 
    
        Resource 注解
        作用：直接按照 bean 的 id 注入，可以独立使用
        属性: name 用于指定 bean 的 id
        
        以上三种注入都只能注入其他 bean 类型的数据，基本类型和 String 类型无法使用上述注解实现。
        另，集合类型数据注入只能通过 xml 来实现
        
        Value 注解
        作用：用于注入基本类型和 String 类型的数据
        属性：value 用于指定数据的值，可以使用 spring 中的 SpEL （也就是 spring 的 EL 表达式）
            SpEL的写法，${表达式}
    */
    @Autowired
    @Quailfier
    private AccountDao accountDao = null;
    ```
* 用于改变作用范围的
    - 作用和在 bean 标签中使用 scope 属性作用一样的
    ``` java
    // com.joush.dao.impl.AccountServiceImpl.java
    /*
        Scope 注解
        作用：用于指定bean的作用范围
        属性：value：指定范围的取值。常用取值：singleton prototype
    */
    @Service("accountServiceImpl")
    @Scope("prototype") // 多例延迟创建
    // @Scope("singleton") // 单例立即创建
    public class AccountServiceImpl implements AccountService {
        // ...
    }
    ```
* 和生命周期相关的
    - 作用和在 bean 标签中使用 init-method 和 destroy-method 属性作用一样的
    ``` java
    // com.joush.dao.impl.AccountServiceImpl.java
    /*
        PreDestroy 注解
            作用：用于指定销毁方法
        PostConstruct 注解
            作用：用于指定初始化方法
    */
    @PostConstruct
    public void init(){
        System.out.println("初始化方法");
    }
    
    @PreDestroy
    public void destroy(){
        System.out.println("销毁方法");
    }
    ```

#### Spring 的新注解

* 使用注解来替代 bean.xml
``` java
// com.joush.config.SpringConfiguration.java

/*
该类是一个配置类，作用和 bean.xml 一样
Configuration 注解
    作用：指定当前类是一个配置类
    细节：当配置类作为 AnnotationConfigApplicationContext 对象创建的参数时，该注解可以不写
ComponentScan 注解
    作用：指定 Spring 在初始化时要扫描的包，等同于bean中的
    <context:component-scan base-package="com.joush"></context:component-scan>
    属性：value: 和 beanPackage 作用一样，用于指定创建容器需要扫描的包，等同于上一句的配置
Bean 注解
    作用：用于把当前方法的返回值作为 bean 对象存入 spring 容器中
    属性: name 用于指定 bean 的 id，默认是当前的方法名
Import 注解
    作用：用于导入其他配置类，即可以在主配置类中添加注解，指明其他配置类
    属性：value：用于指定其他配置类的字节码文件, Import注解写的即是主配置类
PropertiesSource 注解
    作用：用于指定 properties 文件的位置
    属性：value：指定文件的名称和路径
          关键字 classpath 表示类路径下
*/
@Configuration
@ComponentScan(basePackages = "com.joush")
@Import(JdbcConfig.class)
public class SpringConfiguration {
    
    /**
     * 创建一个 QueryRunner 对象
     * @param dataSource
     * @return
     */
    @Bean(name = "runner")
    public QueryRunner createQueryRunner(DataSource dataSource){
        return new QueryRunner(dataSource);
    }

    /**
     * 创建数据源对象
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource createDataSource(){
        ComboPooledDataSource ds = new ComboPooledDataSource();

        try {
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/joush?serverTimezone=UTC");
            ds.setUser("root");
            ds.setPassword("liyihang123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
}

```
* Junit 集成
    - junit 不会知道是否采用了 spring，所以在执行 Test 方法时，不会创建 ioc 核心容器
    - 所以写了 Autowired 注解也不会注入
    ``` java
    /*
        com.joush.test.AccountServiceTest.java
        Spring 整合 Junit 配置
            1.导入 spring 整合 junit 的坐标
            2.使用 Junit 提供的注解，把原有的 main 方法替换成 spring 提供的 main 方法
                @RunWith
            3.告知 spring 运行器，ioc 是基于 xml 还是注解，并说明位置
                @ContextConfiguration
                    locations: 指定 xml 文件的位置
                    classes: 指定注解类所在的位置
        当使用 spring 5.x 版本时，要求 junit 的版本必须是 4.1.2及以上
    */
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = SpringConfiguration.class)
    public class AccountServiceTest {
        // ...
    }
    ```

#### 银行转账案例
* 解决出现的事务问题
``` java 
// com.joush.dao.impl.AccountDaoImpl.java

@Override
public void transfer(String sourceName, String targetName, double money) {
    // 1.根据名称查询转出账户
    Account source = accountDao.findAccountByName(sourceName);  // 拿到第一个链接

    // 2.根据名称查询转入账户
    Account target = accountDao.findAccountByName(targetName);  // 拿到第二个连接

    // 3.转出账户减钱
    source.setMoney(source.getMoney() - money);
    accountDao.updateAccount(source);       // 拿到第三个连接
    
    int a = 1/0; // 此处出现异常，则转账出现问题
    
    // 4.转入账户加钱
    target.setMoney(target.getMoney() + money);
    accountDao.updateAccount(target);       // 拿到第四个连接
    
    /*
        因为四次数据库交互是四次独立的连接，四次事务是独立的，无法有效进行事务控制
        解决方法
            使用 ThreadLocal 对象把 Connection 和当前线程绑定，使得一个线程中只有一个能控制事务的对象
        具体方式看工程 SpringDemo09AccountProject
    */
}
```

#### 动态代理
* 基于接口的动态代理
``` java
// com.joush.proxy.Client.java
/*
    动态代理
        特点：字节码随用随加载
        作用：不修改源码的基础上对方法增强
        分类：基于接口的，基于子类的
    基于接口的动态代理
        涉及的类，Proxy
        提供者：JDK
    如何创建代理对象
        使用 Proxy 类中的 newProxyInstance 方法
    创建代理对象的要求
        被代理类最少实现一个接口，如果没有，则不能使用
    方法签名
    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h);
        参数：
            ClassLoader loader, 类加载器，用于加载代理对象的字节码，和被代理对象使用同样的类加载器，固定写法
            Class<?>[] interfaces, 字节码数组，用于代理对象和被代理对象有相同的方法
            InvocationHandler h, 用于提供增强的代码，写如何代理，一般写一个该接口的实现类，通常写的匿名内部类（非必须）

 */
IProducer proxyProducer = (IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
        producer.getClass().getInterfaces(),
        new InvocationHandler() {
            /**
             * 执行被代理对象的任何接口方法，都会经过该方法
             * @param proxy 代理对象的引用
             * @param method 当前执行的方法
             * @param args 当前执行方法所需的参数
             * @return 和被代理对象方法有相同的返回值
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /*
                    提供增强的代码
                 */
                Object returnValue = null;
                // 获取方法的执行参数
                double money = (double) args[0];

                // 判断当前方法是不是 销售
                if ("saleProduct".equals(method.getName())){
                    returnValue = method.invoke(producer, money * 0.8);
                }

                return returnValue;

            }
        });
proxyProducer.saleProduct(10000d); // 卖电脑，拿钱 8000.0
```
* 基于子类的动态代理
``` java
/*
    动态代理
        特点：字节码随用随加载
        作用：不修改源码的基础上对方法增强
        分类：基于接口的，基于子类的
    基于子类的动态代理
        涉及的类，Enhancer
        提供者：第三方 cglib 库
    如何创建代理对象
        使用 Enhancer 类中的 create 方法
    创建代理对象的要求
        被代理类不能是最终类
    方法签名
    public static Object create(Class type, Callback callback);
        参数：
            Class type, 字节码，被代理对象的字节码
            Callback callback, 用于提供增强的代码，写如何代理，一般写一个该接口的实现类，通常写的匿名内部类（非必须）
                               此接口的实现类都是谁用谁写，我们一般写的是该接口的子接口实现类，MethodInterceptor

 */
Producer cglibProducer = (Producer) Enhancer.create(producer.getClass(), new MethodInterceptor() {
    /**
     * 执行被代理对象的任何方法都会经过该方法
     * @param o 代理对象的引用
     * @param method 当前执行的方法
     * @param objects 当前执行方法所需的参数
     *  以上三个参数和基于接口动态代理中 invoke 方法参数一样的
     * @param methodProxy 当前执行方法的代理对象
     * @return 与执行方法的返回值相同
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        /*
            提供增强的代码
         */
        Object returnValue = null;
        // 获取方法的执行参数
        double money = (double) objects[0];

        // 判断当前方法是不是 销售
        if ("saleProduct".equals(method.getName())) {
            returnValue = method.invoke(producer, money * 0.8);
        }

        return returnValue;
    }
});


cglibProducer.saleProduct(12000d); // 卖电脑，拿钱 9600.0
```
## AoP 

#### AoP(Aspect Oriented Programming, 面向切面编程)

* 概念
    - 通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术
    - AOP 是 OOP 的延续，是软件开发中的一个热点，也是 Spring 框架中的一个重要内容，是函数式编程的一种衍生泛型
    - 利用 AOP 可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时挺高了开发的效率
* 作用和优势
    - 在程序运行期间，不修改源码对已有方法增强
    - 减少重复代码
    - 提高开发效率
    - 维护方便
* 实现方式
    - 动态代理

#### Spring 中的 AoP

* Spring 中的术语
    - Joinpoint，连接点，所谓连接点是指那些被拦截到的点，在 spring 中，这些点指的是方法，因为 spring 只支持方法类型的连接点，通俗来讲，业务层的所有方法都是连接点
    - Pointcut，切入点，所谓切入点是指我们要对哪些 Joinpoint 进行拦截的定义，通俗来讲，业务层中被增强的方法，都叫切入点
    - Advice，通知/增强，拦截之后要做的事情，指的就是通知
        - 通知的类型
            - 前置通知，在 invoke 方法执行之前的即称为前置通知
            - 后置通知，在 invoke 方法执行后的称为后置通知
            - 异常通知，在 catch 块中的代码称为异常通知
            - 最终通知，在 finally 块中的代码称为最终通知
            - 环绕通知，在 invoke 方法执行就是环绕通知，有明确的调用方法，invoke
        ``` java 
        // com.joush.service.impl.AccountServiceImpl.java
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  // 环绕通知
        
            if("test".equals(method.getName())){
                return method.invoke(accountService,args);
            }

            Object returnValue = null;

            try {
                // 1.开启事务
                transactionManager.beginTransaction();          // 前置通知
                // 2.执行操作
                returnValue = method.invoke(accountService, args);
                // 3.提交事务
                transactionManager.commit();    // 后置通知
                // 4.返回结果
                return returnValue;
            } catch (Exception e){      // 异常通知
                // 回滚
                transactionManager.rollback();
                throw new RuntimeException(e);
            } finally {                 // 最终通知
                // 释放资源
                transactionManager.release();
            }
        }
        ```
    - Introduction，引介，引介是一种特殊的通知，在不修改类代码的前提下，Introduction 可以在运行期为类动态地添加一些方法或 Field
    - Target，目标对象，代理的目标对象
    - Weaving，织入，是指把增强应用到目标对象来创建新的代理对象的过程。 spring 采用动态代理织入，而 AspectJ 采用编译期织入和类装载期织入。
    - Proxy，代理，一个类被AOP织入增强后，就产生一个结果代理类
    - Aspect，切面，是切入点和通知（引介）的结合

* spring 中基于 xml 的 aop 配置
``` xml
<!-- source.bean.xml  -->
<!--  配置 Spring Ioc, 配置 service 对象  -->
    <bean id="accountService" class="com.joush.service.impl.AccountServiceImpl"></bean>

    <!--  spring 中基于 xml 的 aop 配置步骤
        1.把通知的 bean 也交给 spring 管理
        2.使用 aop:config 标签表明开始 aop 配置
        3.使用 aop:aspect 标签表明配置切面
            id 属性，给切面提供一个唯一标识
            ref 属性，是指定通知类 bean 的 id
        4.在 aop:aspect 标签内部使用对应的标签来配置通知的类型
            示例是时 printlog 在切入点方法执行之前执行，所以属于前置通知
            app:before 表示配置前置通知
                method 属性：用于指定 Logger 类中哪个方法是前置通知
                pointcut 属性，用于指定切入点表达式，该表达式的含义是指对业务层中哪些方法增强
                切入点表达式的写法
                    关键字：execution
                    表达式：访问修饰符 返回值 包名.包名.包名...类名.方法名
                    标准的表达式写法
                        public void com.joush.service.impl.AccountServiceImpl.saveAccount()
                        访问修饰符可以省略
                            void com.joush.service.impl.AccountServiceImpl.saveAccount()
                        返回值可以使用通配符
                            * com.joush.service.impl.AccountServiceImpl.saveAccount()
                        包名可以使用通配符，表示任意包，但需要写够包的目录
                            * *.*.*.*.AccountServiceImpl.saveAccount()
                        包名可以使用 .. 表示当前包及其子包
                            * *..AccountServiceImpl.saveAccount()
                        类名和方法名都可以使用通配符 *
                            * *..*.*()
                        参数列表
                            可以直接写数据类型
                                基本类型直接写名字   int
                                引用类型写包名.类名 java.long.String
                            可以使用通配符，表示任意类型，必须有参数
                            * *..*.*(*)
                            可以使用 .. 表示有无参数均可，有参数任意类型均可
                            * *..*.*(..)
                    全通配写法
                        * *..*.*(..)

                    实际开发中切入表达式的通常写法
                        切到业务层类下的所有方法
                            * com.joush.service.impl.*.*(..)
    -->
    <!--  配置 Logger 类  -->
    <bean id="logger" class="com.joush.utils.Logger"></bean>

    <!--  配置 aop  -->
    <aop:config>
        <!--  配置切面  -->
        <aop:aspect id="logAdvice" ref="logger">
            <!--  配置通知类型，并且建立通知方法和切入点方法的关联  -->
            <aop:before method="printLog" pointcut="execution(* com.joush.service.impl.*.*(..))"></aop:before>
        </aop:aspect>
    </aop:config>

</beans>
```

* 多通知类型和通用化的切入点表达式
``` xml
<!--  resource.bean.xml  -->
<aop:config>

    <!--  配置切入点表达式
            id 用于指定唯一标识，expression 属性用于指定表达式的内容
                此标签卸载 aop:aspect 标签内部，所以只能当前切面使用
            但，还支持写在 aop:aspect 标签外部，则所有切面均可使用
            
            要求必须配置在切面之前
        -->
    <aop:pointcut id="pt1" expression="execution(* com.joush.service.impl.*.*(..))"/>
    <!--  配置切面  -->
    <aop:aspect id="logAdvice" ref="logger">
        <!--  配置前置通知，切入点方法之前  -->
        <aop:before method="beforePrintLog" pointcut-ref="pt1"></aop:before>

        <!--  配置后置通知，切入点方法之后  -->
        <aop:after-returning method="afterReturningPrintLog" pointcut-ref="pt1"></aop:after-returning>

        <!--  配置异常通知，切入点方法出现异常  -->
        <aop:after-throwing method="afterThrowingPrintLog" pointcut-ref="pt1"></aop:after-throwing>

        <!--  配置最终通知，切入点方法是否异常，一定执行  -->
        <aop:after method="afterPrintLog" pointcut-ref="pt1"></aop:after>
        
    </aop:aspect>
</aop:config>
```

#### Spring JdbcTemplate
* JdbcTemplate 的基本用法
``` java 
// com.joush.template.JdbcTemplateDemo01.java

// 0.配置数据源
DriverManagerDataSource dataSource = new DriverManagerDataSource();
dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
dataSource.setUrl("jdbc:mysql://localhost:3306/joush?serverTimezone=UTC");
dataSource.setUsername("root");
dataSource.setPassword("liyihang123");

// 1.创建 JdbcTemplate 对象
JdbcTemplate jdbcTemplate = new JdbcTemplate();

jdbcTemplate.setDataSource(dataSource);

// 2.执行操作
jdbcTemplate.execute("insert into new_account (name, money) values ('ddd', 1000)");
```

* JdbcTemplate 基于 Spring Ioc 的用法
``` xml
<!--  resource.bean.xml  -->

<!--  配置 jdbcTemplate  -->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <property name="dataSource" ref="dateSource"></property>
</bean>

<bean id="dateSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
    <property name="url" value="jdbc:mysql://localhost:3306/joush?serverTimezone=UTC"></property>
    <property name="username" value="root"></property>
    <property name="password" value="liyihang123"></property>
</bean>
```
``` java
// com.joush.template.JdbcTemplateDemo01.java

// 1.获取容器
ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

// 2.获取对象
JdbcTemplate template = ac.getBean("jdbcTemplate", JdbcTemplate.class);

// 3.执行操作
jdbcTemplate.execute("insert into new_account (name, money) values ('eee', 1000)");
```
* JdbcTemplate 的 CRUD 操作
``` java
// com.joush.template.JdbcTemplateDemo03.java

// 1.获取容器
ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

// 2.获取对象
JdbcTemplate template = ac.getBean("jdbcTemplate", JdbcTemplate.class);

// 3.执行操作

// 保存
template.update("insert into new_account (name, money) values (?,?)", "fff",1000d);

// 更新
template.update("update new_account set money = ? where id = ?", 2000d, 6);

// 删除
template.update("delete from new_account where id = ?", 6);

// 查询所有，需要考虑封装结果集
List<Account> accounts = template.query("select * from new_account", new AccountRowMapper()); // 自己写实现类，查看 com.joush.template.JdbcTemplateDemo03.AccountRowMapper.java
List<Account> accounts = template.query("select * from new_account", new BeanPropertyRowMapper<Account>(Account.class)); // spring 写实现类
for (Account account : accounts){
    System.out.println(account);
}

// 查询一个
List<Account> accounts2 = template.query("select * from new_account where id = ?", new BeanPropertyRowMapper<Account>(Account.class), 1);
System.out.println(accounts2.isEmpty()? "没有结果" : accounts2.get(0));

// 查询返回一行一列（使用聚合函数，不加 group by 子句）
long integer = template.queryForObject("select count(*) from new_account where money > ?", Long.class, 900d);
System.out.println(integer); // 4
```
* Spring 中的 JdbcDaoSupport 使用
``` java
// com.joush.dao.impl.AccountDaoImpl.java

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public Account findAccountById(int id) {
                                  // 此处如果有很多 dao，只需要继承 spring 提供的 JdbcDaoSupport 即可，不需要重复定义 jdbcTemplate
        List<Account> accounts = super.getJdbcTemplate().query("select * from new_account where id = ?", new BeanPropertyRowMapper<Account>(Account.class), id);
        return accounts.isEmpty()? null : accounts.get(0);
    }
```
* 注解 aop 的问题
``` java
// com.joush.utils.TransactionManager.java
/*
    在使用注解配置的 aop 时，四个通知会出现问题，从而导致无法提交事务，所以使用环绕通知来控制事务
*/
/**
 * 环绕通知实现事务
 * @param proceedingJoinPoint
 * @return
 */
@Around("pt1()")
public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

    Object returnValue = null;

    try {
        // 1.获取参数
        Object[] args = proceedingJoinPoint.getArgs();

        // 2.开启事务
        this.beginTransaction();

        // 3.执行方法
        returnValue = proceedingJoinPoint.proceed(args);

        // 4.提交事务
        this.commit();

        return returnValue;
    } catch (Throwable e){
        // 5.回滚事务
        this.rollback();
        throw new RuntimeException(e);
    } finally {
        // 释放资源
        this.release();
    }
}
```

#### Spring 中的事务管理

* spring 中基于 XML 的声明式事务控制
``` xml
<!--  resource.bean.xml  -->
<!--  spring 中基于 XML 的声明式事务控制配置步骤

    1.配置事务管理器
    2.配置事务通知
        需要导入事务约束 tx 名称空间和约束，同时也需要 aop 的
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xmlns:aop="http://www.springframework.org/schema/aop"
               xmlns:tx="http://www.springframework.org/schema/tx"
               xsi:schemaLocation="
                http://www.springframework.org/schema/beans
                http://www.springframework.org/schema/beans/spring-beans.xsd
                http://www.springframework.org/schema/tx
                http://www.springframework.org/schema/tx/spring-tx.xsd
                http://www.springframework.org/schema/aop
                http://www.springframework.org/schema/aop/spring-aop.xsd">
        使用 tx:advice 标签配置事务通知
            属性
                id 事务通知的唯一标识
                transaction-manager 给事务通知提供事务管理器的引用
    3.配置 aop 的切入点表达式
    4.建立事务通知和切入点表达式的关系
    5.配置事务的属性
        在事务通知 tx:advice 标签内部
  -->

<!--  配置事务管理器  -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dateSource"></property>
</bean>

<!--  配置事务通知  -->
<tx:advice id="txAdvice" transaction-manager="transactionManager">

    <!--
        配置事务的属性
        isolation: 用于指定事务的隔离级别，默认值是 DEFAULT，表示使用数据库的默认级别
        read-only: 用于指定事务是否只读，只有查询方法可以设置为 true，默认为 false，表示读写
        timeout: 用于指定事务的超时时间，默认为 -1，表示永不超时，可以指定数值，以秒为单位
        propagation: 用于指定事务的传播行为，默认值是 REQUIRED，表示一定会有事务，增删改的选择，查询方法可以选择 SUPPORTS
        rollback-for: 用于指定一个异常，当产生该异常时，事务回滚，产生其他异常，不回滚。没有默认值，表示任何异常都回滚
        no-rollback-for: 用于指定一个异常，当产生该异常时，事务不回滚，产生其他异常，都回滚。没有默认值，表示任何异常都回滚
        -->
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED" read-only="false"/> <!--  所有方法  -->
        <tx:method name="find*" propagation="SUPPORTS" read-only="true" /> <!--  查询方法以 find 开头  -->
    </tx:attributes>
</tx:advice>

<!--  配置aop  -->
<aop:config>
    <!--  配置切入点表达式  -->
    <aop:pointcut id="pt1" expression="execution(* com.joush.service.impl.*.*(..))"></aop:pointcut>

    <!--  建立切入点表达式和事务通知的对应关系  -->
    <aop:advisor advice-ref="txAdvice" pointcut-ref="pt1"></aop:advisor>

</aop:config>
```

* spring 中基于注解的声明式事务控制
``` xml
<!--  resource.bean.xml  -->
<!--  spring 中基于 XML 的声明式事务控制配置步骤

    1.配置事务管理器
    2.开启 spring 对注解事务的支持
    3.在需要事务支持的地方使用 @Transactional 注解
  -->

<!--  配置事务管理器  -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dateSource"></property>
</bean>

<!--  开启 spring 对注解事务的支持  -->
<tx:annotation-driven transaction-manager="transactionManager"></tx:annotation-driven>
```
``` java
// com.joush.service.impl.AcccountServiceImpl.java

@Service("accountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true) // 查询型配置
public class AccountServiceImpl implements AccountService {
    // ...

    // 需要读写型配置
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void transfer(String sourceName, String targetName, double money) {
        // ...
    }

    // ...
}

/**
 * 因为使用注解型的配置，在 service 中的方法都需要配置读写型还是只读型，所以更推荐使用基于 xml 的配置
 */
    
```