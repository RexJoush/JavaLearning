# 一、基础知识

## 1.分布式理论

#### 1.1 什么是分布式系统？

* 《分布式系统原理与范型》定义
  * 分布式系统是若干独立计算机的集合，这些计算机对于用户来说就像单个相关系统
  * 分布式系统（distributed system）是建立在网络之上的软件系统。
  * 随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，亟需**一个治理系统**确保架构有条不紊的演进。

#### 1.2 发展演变

* 单一应用架构
  * 当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。此时，用于简化增删改查工作量的数据访问框架(ORM)是关键
  * 适用于小型网站，小型管理系统，将所有功能都部署到一个功能里，简单易用。
  * 缺点： 
    * 1、性能扩展比较难
    * 2、协同开发问题
    * 3、不利于升级维护
* 垂直应用架构
  * 当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架(MVC)是关键。
  * 通过切分业务来实现各个模块独立部署，降低了维护和部署的难度，团队各司其职更易管理，性能扩展也更方便，更有针对性。
  * 缺点： 公用模块无法重复利用，开发性的浪费
* 分布式服务架构
  * 当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的**分布式服务框架****(RPC)**是关键
* 流动计算架构
  * 当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。
  * 此时，用于**提高机器利用率的资源调度和治理中心(SOA)[ Service Oriented Architecture]是关键**。

#### 1.3 RPC

* 什么叫 RPC

  * RPC【Remote Procedure Call】是指远程过程调用，是一种进程间通信方式，他是一种技术的思想，而不是规范。
  * 它允许程序调用另一个地址空间（通常是共享网络的另一台机器上）的过程或函数，而不用程序员显式编码这个远程调用的细节。即程序员无论是调用本地的还是远程的函数，本质上编写的调用代码基本相同。

* RPC 基本原理

  ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/Dubbo/rpcprocess.jpg?raw=true)

* RPC两个核心模块：通讯，序列化。

## 2.dubbo 核心概念

#### 2.1 简介

* Apache Dubbo (incubating) |ˈdʌbəʊ| 是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。
* [官网](https://dubbo.apache.org/zh/index.html)

#### 2.2 基本概念

![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/Dubbo/dubboarc.jpg?raw=true)

* 服务提供者（Provider）：暴露服务的服务提供方，服务提供者在启动时，向注册中心注册自己提供的服务。
* 服务消费者（Consumer）: 调用远程服务的服务消费方，服务消费者在启动时，向注册中心订阅自己所需的服务，服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
* 注册中心（Registry）：注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者
* 监控中心（Monitor）：服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心
* 调用关系说明
  * 服务容器负责启动，加载，运行服务提供者
  * 服务提供者在启动时，向注册中心注册自己提供的服务
  * 服务消费者在启动时，向注册中心订阅自己所需的服务
  * 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者
  * 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用
  * 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

## 3.Dubbo环境搭建

#### 3.1 安装 Zookeeper

* 此处参考 [zookeeper 教程](https://github.com/RexJoush/JavaLearning/blob/master/Zookeeper/zookeeper.md#21-%E6%9C%AC%E5%9C%B0%E6%A8%A1%E5%BC%8F%E5%AE%89%E8%A3%85)

#### 3.2 安装dubbo-admin管理控制台

* dubbo 本身并不是一个服务软件。它其实就是一个jar包能够帮你的java程序连接到zookeeper，并利用zookeeper消费、提供服务。所以你不用在Linux上启动什么dubbo服务

* 但是为了让用户更好的管理监控众多的dubbo服务，官方提供了一个可视化的监控程序，不过这个监控即使不装也不影响使用。

* 1下载dubbo-admin

  * `git clone https://github.com/apache/dubbo-admin.git`

  * 进入目录dubbo-admin，修改dubbo-admin配置，修改 src\main\resources\application.properties 指定zookeeper地址

    ```properties
    spring.guest.password=guest
    dubbo.gegistry.address=zookeeper://127.0.0.1:2181
    ```

* 2.打包dubbo-admin `mvn clean package -Dmaven.test.skip=true`

* 3.运行dubbo-admin `java -jar dubbo-admin-0.0.1-SNAPSHOT.jar`

* 4.登录 dubbo `localhost:8080`，用户名 root，密码 root

* 5.如果此处登录出现异常 `java.lang.NoClassDefFoundError: javax/xml/bind/DatatypeConverter`

  ```xml
  <!-- 在 dubbo-admin-server 的 pom 中添加下面依赖即可 -->
  <dependency>
      <groupId>javax.xml.bind</groupId>
      <artifactId>jaxb-api</artifactId>
      <version>2.3.0</version>
  </dependency>
  <dependency>
      <groupId>com.sun.xml.bind</groupId>
      <artifactId>jaxb-impl</artifactId>
      <version>2.3.0</version>
  </dependency>
  <dependency>
      <groupId>com.sun.xml.bind</groupId>
      <artifactId>jaxb-core</artifactId>
      <version>2.3.0</version>
  </dependency>
  <dependency>
      <groupId>javax.activation</groupId>
      <artifactId>activation</artifactId>
      <version>1.1.1</version>
  </dependency>
  ```

## 4.dubbo-helloworld

#### 4.1 提出需求

* 某个电商系统，订单服务需要调用用户服务获取某个用户的所有地址；我们现在需要创建两个服务模块进行测试

  | 模块                | 功能           |
  | ------------------- | -------------- |
  | 订单服务web模块     | 创建订单等     |
  | 用户服务service模块 | 查询用户地址等 |

* 测试预期结果： 订单服务web模块在A服务器，用户服务模块在B服务器，A可以远程调用B的功能。

#### 4.2 新建三个工程

* 详见 DubboDemo01

#### 4.3 具体流程

* 1.将服务提供者注册到注册中心（暴露服务）

  * 导入依赖

    ```xml
    <!-- 引入 Dubbo  -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>dubbo</artifactId>
        <version>2.6.12</version>
    </dependency>
    
    <!--  引入操作 zookeeper 的客户端 -->
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-framework</artifactId>
        <version>5.2.1</version>
    </dependency>
    ```

  * 配置服务提供者

    * 新建 xml

      ```xml
      <!-- resources/provider.xml -->
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.3.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
      
          <!-- 1.指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名） -->
          <dubbo:application name="user-service-provider"/>
      
          <!-- 2.指定注册中心的位置 -->
          <dubbo:registry address="zookeeper://hadoop01:2181?backup=hadoop02:2181,hadoop03:2181" />
      
          <!-- 3.指定通信规则（协议？端口）
              此处支持的协议很多，默认是 dubbo
          -->
          <dubbo:protocol name="dubbo" port="20881"/>
      
          <!-- 4.暴露的服务, ref 指向 服务的真正实现，下面的 bean -->
          <dubbo:service interface="com.joush.service.UserService" ref="userService"/>
      
          <!-- 和本地bean一样实现服务 -->
          <bean id="userService" class="com.joush.service.impl.UserServiceImpl"/>
      </beans>
      ```

    * 前往 `localhost:8080` 即可看到服务

* 2.让服务消费者去注册中心订阅服务提供者的服务地址

  * 同样的，引入依赖，提供者一样

  * 配置 consumer.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
                http://www.springframework.org/schema/beans/spring-beans.xsd
                http://www.springframework.org/schema/context
                http://www.springframework.org/schema/context/spring-context.xsd
                http://dubbo.apache.org/schema/dubbo
                https://dubbo.apache.org/schema/dubbo/dubbo.xsd">
    
        <context:component-scan base-package="com.joush.service.impl" />
    
        <!-- 1.指定当前服务/应用的名字（同样的服务名字相同，不要和别的服务同名） -->
        <dubbo:application name="order-service-consumer"/>
    
        <!-- 2.指定注册中心的位置 -->
        <dubbo:registry address="zookeeper://hadoop01:2181?backup=hadoop02:2181,hadoop03:2181" />
    
        <!-- 声明需要调用的远程服务接口，生成远程服务代理，此处的 interface 是之上面提供者暴露的 -->
        <dubbo:reference interface="com.joush.service.UserService" id="userService"/>
    
    </beans>
    ```

#### 4.4 Dubbo 整合 Springboot

* 新建三个模块，详见 `DubboDemo02Springboot`

* 导入 dubbo-starter 依赖

  ```xml
  <!-- 导入 dubbo -->
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-spring-boot-starter</artifactId>
      <version>3.0.6</version>
  </dependency>
  <dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo-dependencies-zookeeper</artifactId>
      <version>3.0.6</version>
      <type>pom</type>
      <exclusions>
          <exclusion>
              <groupId>org.slf4j</groupId>
              <artifactId>slf4j-log4j12</artifactId>
          </exclusion>
      </exclusions>
  </dependency>
  ```

* 写入配置

  ```properties
  # provider.resource.properties
  # 应用名称
  spring.application.name=boot-user-service-provider
  
  # 服务名称
  dubbo.application.name=user-service-provider
  
  # 注册中心 zookeeper
  dubbo.registry.address=hadoop01:2181?backup=hadoop02:2181,hadoop03:2181
  dubbo.registry.protocol=zookeeper
  
  dubbo.protocol.name=dubbo
  dubbo.protocol.port=20881
  
  # 监控中心
  dubbo.monitor.protocol=registry
  ```

  ```properties
  # consumer.resource.properties
  
  # 应用名称
  spring.application.name=boot-order-service-consumer
  # 应用服务 WEB 访问端口
  server.port=8081
  
  # 应用名字
  dubbo.application.name=order-service-consumer
  
  # 注册中心 zookeeper
  dubbo.registry.address=hadoop01:2181?backup=hadoop02:2181,hadoop03:2181
  dubbo.registry.protocol=zookeeper
  
  # 监控中心
  dubbo.monitor.protocol=registry
  ```

* 在需要暴露的服务上加注解 `@DubboService`

  ```java
  // provider com.joush.service.impl
  @DubboService // 配置为 dubbo 服务暴露应用
  @Service
  public class UserServiceImpl implements UserService {
  
      @Override
      public List<UserAddress> getUserAddressList(String userId) {
          UserAddress address1 = new UserAddress(1, "北京中关村", "1", "Rex", "010-45672412", "Y");
          UserAddress address2 = new UserAddress(2, "陕西西北大学", "1", "Rex", "010-45672412", "N");
  
          return Arrays.asList(address1, address2);
      }
  }
  ```

* 在需要消费的地方注入对象，不再用 `@Autowired` 或者 `@Resources`, 而使用 `@DubboReference`

  ```java
  // consumer com.joush.service.impl
  @Service
  public class OrderServiceImpl implements OrderService {
  
      // 此处就可以注入了
      @DubboReference
      private UserService userService;
  
      @Override
      public List<UserAddress> initOrder(String userId) {
          System.out.println("userId: " + userId);
          // 1.查询用户的收获地址
          return userService.getUserAddressList(userId);
      }
  }
  ```

* 在主启动类上均加上注解 `@EnableDubbo`
