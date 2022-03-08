## Spring Cloud

#### 新建父工程

* 1.New Project
    * 新建 maven 工程
    * 选择 `maven-archetype-site` 项目骨架
* 2.聚合总父工程名
* 3.maven 版本
* 4.工程名
* 5.字符编码
* 6.注解生效激活
* 7.java 编译版本
* 8.File Type 过滤

## Introduce

#### `pom.xml`
* Maven 使用 dependencyManagement 元素来提供了一种管理依赖版本号的方式。通常会在一个组织或者项目的最顶层的父 POM 中看到 dependencyManagement
* 使用 pom.xml 中的 dependencyManagement 元素能让所有在子项目中引用一个依赖而不用显示的列出版本号
  Maven 会沿着父子层级向上走，直到找到一个拥有 dependencyManagement 元素的项目，然后它就会使用这个
  dependencyManagement 元素中指定的版本号
* 这样做的好处就是：如果有多个子项目都引用同一个依赖，则可以避免在每个使用的子项目里都声明一个版本号
  这样当想升级或切换到另一个版本时，只需要在顶层父容器里更新，而不需要一个个子项目的修
  另外如果某个子项目需要另外的一个版本，只需要声明 version 即可
* dependencyManagement 里只是声明依赖，并不实现引入，因此子项目需要显示的声明需要用到的依赖
* 如果不在子项目中声明依赖，是不会从父项目中继承下来的，只有在子项目中写了该依赖，并且没有指定具体版本，才会从父项目中继承该项，并且 version 和 scope 都读自父 pom
* 如果项目中指定了版本号，那么会使用子项目中指定的 jar 版本

* example
```xml
<!--  父 pom-->
<dependencyManagement>
    <dependencies>
      <!--  spring boot 2.4.3  -->
      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.23</version>
      </dependency>
    </dependencies>
</dependencyManagement>

<!--  子 pom, 不用写 version  -->
<dependencies>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
  </dependency>
</dependencies>
```

## 新建微服务模块

#### 1.建 module
* 新建一个 module 项目
#### 2.改 pom
* 修改新的 module 项目的 pom 文件，引入依赖
* 父 pom 有的，不需要写版本号，没有的需要加入版本号
#### 3.写 yml
* 在新模块的 resource 下新建 `application.yml` 添加配置文件
```yaml
server:
  port: 8001
  
spring:
  application:
    name: cloud-payment-service
```
#### 4.主启动
* 在 java 下新建主启动类 `com.joush.PaymentMain8001.java`
```java
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class);
    }
}
```
#### 5.业务类
* 建表
* entities
* dao
* service
* controller

## 微服务之间的调用

#### RestTemplate

* RestTemplate 提供了多种便捷访问远程 http 服务的方法
* 是一种简单便捷的访问 restful 服务模块类，是 Spring 提供的用于访问 Rest 服务的客户端模板工具集

#### 使用

* 新建配置类，注入 Bean 对象

    ```java
    // com.joush.config.ApplicationContextConfig.java
    @Configuration
    public class ApplicationContextConfig {
    	
        // 注入 RestTemplate
        @Bean
        public RestTemplate getRestTemplate(){
            return new RestTemplate();
        }
    }
    ```

* 使用 template 发送 restful 请求

    ```java
    @RestController
    public class OrderController {
    	// 定义请求的另一个微服务的IP地址
        public static final String PAYMENT_URL = "http://localhost:8001";
    	
        // 注入 template
        @Resource
        private RestTemplate restTemplate;
    	
        @GetMapping("/consumer/payment/create")
        public CommonResult<Payment> create(Payment payment){】
            // post
            return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
        }
    	
        @GetMapping("/consumer/payment/get/{id}")
        public CommonResult<Payment> getPayment(@PathVariable("id") int id){
            // get
            return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
        }
    }
    ```

#### 工程重构

* 几个模块中有公有部分，所有抽取出来变成公共部分

* 步骤

    * 1.新建公共模块 `cloud-api-commons`

    * 2.使用spring initializr 的话，需要修改 parent，以及父 pom 中的 module

    * 3.在要使用公共模块的子模块中引入依赖

        ```xml
        <dependency>
            <groupId>org.joush</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        ```

## Eureka

#### 什么是服务注册与发现

```text
    Eureka 采用了 CS 的设计架构，Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务,使用 Eureka 的客户端连接到 Eureka Server 并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。

    在服务注册与发现中，有一个注册中心。 当服务器启动的时候，会把当前自己服务器的信息比如服务地址通讯地址等以别名方式注册到注册中心上。另一方（消费者|服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址,然后再实现本地 RPC 调用 RPC 远程调用框架核心设计思想:在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系(服务治理概念)。在任何 RPC 远程框架中，都会有一个注册中心（存放服务地址相关信息（接口地址）
```

#### Eureka Server

* Eureka Server 提供服务注册，割割微服务节点，通过配置启动后，会在 Eureka Server 中进行注册，这样 Eureka Server 中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在页面中直观看到

#### Eureka Client

* Eureka Client 是一个 Java 客户端，用于简化 Eureka Server 的交互，客户端同时也具备一个内置的，使用轮询负载算法的负载均衡器。在应用启动后，将会向 Eureka Server 发送心跳（默认周期是 30s）。如果 Eureka Server 在多个心跳周期内没有收到某个节点的心跳，Eureka Server 将会从服务注册表中将此服务节点移出，默认90s。

#### 新建 cloud-eureka-server7001 模块

* 引入依赖 `spring-boot-start-netflix-eureka-server`

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    ```

* 添加配置

    ```yaml
    # application.yml
    server:
      port: 7001
    eureka:
      instance:
        hostname: localhost # eureka 服务端实例名称
      client:
        register-with-eureka: false # false 表示不向注册中心注册自己
        fetch-registry: false # 表示自己端就是注册中心，我的职责就是维护服务实例，不需要去检索服务
        service-url:
          # 设置与 Eureka Server 交互的地址，查询服务和注册服务都需要依赖这个地址
          defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
    ```

* 新建主启动类，标明注册中心

    ```java
    @SpringBootApplication
    @EnableEurekaServer // 标明注册中心
    public class Eureka7001Application {
    
        public static void main(String[] args) {
            SpringApplication.run(Eureka7001Application.class);
        }
    
    }
    ```

* 启动 eureka 服务

    打开 `http://localhost:7001` 查看到 Eureka 页面，表示配置成功

#### 将另外的微服务模块注册进 eureka 

* 修改 pom，引入 client 依赖

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```

* 在主启动类添加注解 @EnableEurekaClient

* 修改配置文件

    ```yaml
    # application.yml
    
    mybatis:
      mapper-locations: classpath:mapper/**/*Mapper.xml
      type-aliases-package: com.joush.entities  # 所有 entity 别名类所在的包
    
    eureka:
      client:
        register-with-eureka: true # 将自己注册进注册中心
        # 是否从 Eureka Server 读取自己的注册信息，单节点无所谓，集群必须设置为 true 才能配合 ribbon 使用负载均衡
        fetch-registry: true 
        service-url:
          defaultZone: http://localhost:7001/eureka
    ```

    

#### Eureka 集群

* 新建 cloud-eureka-server7002

    ```yml
    # 前面依赖和 7001 一样，引入即可，修改配置文件，主要修改 hostnameW 和端口号，以及 defaultZone
    # application.yml
    server:
      port: 7002
    eureka:
      instance:
        hostname: eureka7002.com # eureka 服务端实例名称
      client:
        register-with-eureka: false # false 表示不向注册中心注册自己
        fetch-registry: false # 表示自己端就是注册中心，我的职责就是维护服务实例，不需要去检索服务
        service-url:
          # 设置与 Eureka Server 交互的地址，查询服务和注册服务都需要依赖这个地址
          defaultZone: http://eureka7001.com:7001/eureka/ # 互相注册，所以要写另一个注册中心的地址
    ```

    

* 配置 hosts

    ```shell
    ### spring cloud ###
    127.0.0.1 eureka7001.com
    127.0.0.1 eureka7002.com
    ```

* 修改 cloud-eureka-server7001 的配置文件

* 测试集群

    * 访问 `http://eureka7001.com:7001` 能看到 DS Replicas 中显示了 eureka7002.com
    * 访问 `http://eureka7002.com:7002` 能看到 DS Replicas 中显示了 eureka7001.com

    * 表示注册成功

* 将 payment 模块注册到 2 台 eureka 集群中

    * 修改 `application.yml` 中的 service-url

        ```yml
        server:
          port: 8001
        
        spring:
          application:
            name: cloud-payment-service
          datasource:
            type: com.alibaba.druid.pool.DruidDataSource
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: jdbc:mysql://localhost:3306/javatest?serverTimezone=UTC
            username: root
            password: 123456
        
        
        mybatis:
          mapper-locations: classpath:mapper/**/*Mapper.xml
          type-aliases-package: com.joush.entities  # 所有 entity 别名类所在的包
        
        eureka:
          client:
            register-with-eureka: true # 将自己注册进注册中心
            fetch-registry: true # 是否从 Eureka Server 读取自己的注册信息，单节点无所谓，集群必须设置为 true 才能配合 ribbon 使用负载均衡
            service-url:
              # defaultZone: http://localhost:7001/eureka # 单机版
              defaultZone: http://eureka7001.com:7001/eureka, http://eureka7002.com:7002/eureka # 集群版
        ```

    * 测试
        * 启动 eureka 7001，7002 服务
        * 启动服务提供者，8001
        * 启动消费者，80

#### 微服务（provider）集群

* 新建 module `cloud-provider-payment8002`

* 修改配置文件

    ```yml
    server:
      port: 8002
    ```

* Controller 层添加端口属性

    ```java
    @RestController
    @Slf4j
    public class PaymentController {
    
        @Resource
        private PaymentService paymentService;
    	
        // 添加端口属性，区分调用当前被调用的微服务
        @Value("${server.port}")
        private String serverPort;
    
        @PostMapping("/payment/create")
        public CommonResult<Integer> create(@RequestBody Payment payment) {
            int i = paymentService.create(payment);
            log.info("结果 " + i);
            if (i > 0) {
                return new CommonResult<>(200, "插入成功, serverPort: " + serverPort, i);
            } else {
                return new CommonResult<Integer>(444, "插入失败");
            }
        }
    
    }
    ```

* 将 cloud-consumer-order80 调用的请求地址，改成微服务的名称

    ```java
    @RestController
    public class OrderController {
    	// 此处将写死的 url 改成 eureka 页面的服务名称
        // public static final String PAYMENT_URL = "http://localhost:8001";
        public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    
        @Resource
        private RestTemplate restTemplate;
    
        @GetMapping("/consumer/payment/create")
        public CommonResult<Payment> create(Payment payment){
            return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
        }
    
        @GetMapping("/consumer/payment/get/{id}")
        public CommonResult<Payment> getPayment(@PathVariable("id") int id){
            return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
        }
    
    }
    
    ```

* 开启 RestTemplate 的负载均衡功能，添加 @LoadBalanced注解

    ````java
    // order80 -- com.joush.config.ApplicationContextConfig.java
    @Configuration
    public class ApplicationContextConfig {
    
        @Bean
        @LoadBalanced
        public RestTemplate getRestTemplate(){
            return new RestTemplate();
        }
    
    }
    ````

* 测试请求地址 `http://localhost/consumer/payment/get/6`

    ```json
    // 出现两种情况，8001，8002 轮询的执行请求，表示配置成功
    {
        code: 200,
        message: "查询成功, serverPort: 8002",
        data: {
            id: 6,
            serial: "ccc"
        }
    }
    ```

#### actuator 信息完善修改

* 主机名称，服务名称修改

    * 添加配置项，将 payment8001 和 payment8002 两个项目的配置文件添加下面内容

        ```yaml
        eureka:
          client:
            register-with-eureka: true
            fetch-registry: true
            service-url:
              defaultZone: http://eureka7001.com:7001/eureka, http://eureka7002.com:7002/eureka # 集群版
          # 此处，配置 instance id 即可
          instance:
            instance-id: payment8002
            prefer-ip-address: true # 访问路径可以显示 id
        ```

* 主机名称，显示访问 id，参考上面配置

#### 服务发现 Discovery

* 对于注册进 eureka 里的微服务，可以通过服务发现来获得该服务的信息

#### Eureka 自我保护

* 概述

    ```text
    保护模式主要用于一组客户端和 Eureka Server 之间存在网络分区场景下的保护，一旦进入保护模式，Eureka Server 将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据，也就是不会注销任何微服务。
    
    如果在 Eureka Server 的首页看到下面这段提示，则说明 Eureka Server 进入了保护模式
    
    EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.
    ```

* 导致原因
    * 某时刻，某一个微服务不可用了，Eureka 不会立刻清理，依旧会对该微服务的信息进行保存
    * 属于 CAP 里面的 AP 分支
    
* 为什么会产生 Eureka 自我保护机制
    * 为了防止，Eureka Client 可以正常运行但与 Eureka Server 网络不通的情况下，Eureka Server 不会立刻将 Eureka Client 服务剔除

* 什么是自我保护模式
    * 默认情况下，如果 Eureka Server 在一定时间内，没有接收到某个微服务实例的心跳，Eureka Server 将会注销该实例（90s）。
    * 当前网络分区故障发生（延时，卡顿，拥挤）时，微服务与 Eureka Server 之间无法正常通信，以上行为可能变得很危险，即微服务本身是健康的，此时不应该注销这个服务
    * Eureka 通过“自我保护模式”来解决这个问题，当 Eureka Server 节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。
    * 它的设计哲学就是，宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例

* 关闭自我保护模式

    * 添加配置，server 端 `cloud-eureka-server7001`

        ```yaml
        # cloud-eureka-server7001
        eureka: 
          server:
            enable-self-preservation: false # 关闭自我保护机制
            eviction-interval-timer-in-ms: 2000 # 2s 收不到心跳信号，就关闭，默认是 90s
        ```

    * 添加配置，client 端 `cloud-provider-payment8001`

        ```yaml
        eureka:
          instance:
            lease-renewal-interval-in-seconds: 1 # 服务向注册中心发送心跳的间隔，默认是 30s
            lease-expiration-duration-in-seconds: 2 # Eureka 服务端在收到最后一次心跳后等待的时间上限，默认是 90s
        ```

## Zookeeper

* 暂时跳过 zookeeper

## Consul

#### 安装 Consul

* 官网下载 [Consule](https://learn.hashicorp.com/tutorials/consul/get-started-install?in=consul/getting-started)

* 运行
    * 双击运行
    * 后台运行 `consul agent -dev`
* 访问 `http://localhost:8500` 即可

#### 服务提供者

* 新建 `cloud-provider-consul-payment8003` 模块

* 添加依赖

    ```xml
    <dependencies>
    
        <!--  spring boot consul server  -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
    
        <!--  web  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    
        <!--  基础通用依赖  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
    ```

* Yaml 配置文件

    ```yaml
    server:
      port: 8003
    
    spring:
      application:
        name: consul-provider-payment
    
      # consul 注册中心地址
      cloud:
        consul:
          host: localhost
          port: 8500
          discovery:
            # hostname 127.0.0.1
            service-name:  ${spring.application.name}
    ```

* 主启动类

    ```java
    @EnableDiscoveryClient // 注册 consul
    @SpringBootApplication
    public class PaymentMain8003 {
    
        public static void main(String[] args) {
            SpringApplication.run(PaymentMain8003.class);
        }
    
    }
    ```

* controller 业务类

    ```java
    // com.joush.comtroller.PaymentController.java
    @RestController
    @Slf4j
    public class PaymentController {
        
        @Value("${server.port}")
        private String serverPort;
    
        @GetMapping("/payment/consul")
        public String paymentConsul() {
            return "Spring Cloud with consul: " + serverPort + "\t" + UUID.randomUUID().toString();
        }
    
    }
    ```

* 测试
    * 访问 `http://localhost:8500/ui/dc1/services` 即可看到 `consul-provider-payment` 注册进了服务中心

#### 服务消费者

* 新建 `cloud-consumer-consul-order80` 模块

* 添加依赖

    ```xml
    <dependencies>
    
        <!--  spring boot consul server  -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
    
        <!--  web  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    
        <!--  基础通用依赖  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
    ```

* Yaml 配置文件

    ```yaml
    server:
      port: 80
    
    spring:
      application:
        name: cloud-consumer-order
    
      # consul 注册中心地址
      cloud:
        consul:
          host: localhost
          port: 8500
          discovery:
            # hostname 127.0.0.1
            service-name:  ${spring.application.name}
    ```

* 主启动类

    ```java
    @EnableDiscoveryClient // 注册 consul
    @SpringBootApplication
    public class ConsulOrderMain80 {
    
        public static void main(String[] args) {
            SpringApplication.run(ConsulOrderMain80.class);
        }
    
    }
    ```

* 配置 Bean

    ```java
    import org.springframework.cloud.client.loadbalancer.LoadBalanced;
    
    // com.joush.config.ApplicationContextConfig.java
    @Configuration
    public class ApplicationContextConfig {
    
        @Bean
        @LoadBalanced
        public RestTemplate getRestTemplate(){
            return new RestTemplate();
        }
    
    }
    ```

* controller 业务类

    ```java
    // com.joush.comtroller.PaymentController.java
    @RestController
    @Slf4j
    public class PaymentController {
        
        @Value("${server.port}")
        private String serverPort;
    
        @GetMapping("/payment/consul")
        public String paymentConsul() {
            return "Spring Cloud with consul: " + serverPort + "\t" + UUID.randomUUID().toString();
        }
    
    }
    ```

* 测试
  * 访问 `http://localhost:8500/ui/dc1/services` 即可看到 `cloud-consumer-order` 注册进了服务中心
  * 访问 `http://localhost/consumer/payment/consul` 即可查看到和访问 payment 一样的结果

#### 三个注册中心的异同
