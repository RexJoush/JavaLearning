## Spring MVC

#### 关于三层架构和 MVC

* 表现层（Spring MVC）
    - 也就是我们常说的 web 层。它负责接收客户端请求，向客户端响应结果，通常客户端使用 http 协议请求 web 层，web 需要接收 http 请求，完成 http 响应。
    - 表现层包括展示层和控制层：控制层负责接收请求，展示层负责结果的展示。表现层依赖业务层，接收到客户端请求一般会调用业务层进行业务处理，并将处理结果响应给客户端。
    - 表现层的设计一般都使用 MVC 模型。（ MVC 是表现层的设计模型，和其他层没有关系）
* 业务层（Spring）
    - 也就是我们常说的 service 层。它负责业务逻辑处理，和我们开发项目的需求息息相关。web 层依赖业务层，但是业务层不依赖 web 层。 
    - 业务层在业务处理时可能会依赖持久层，如果要对数据持久化需要保证事务一致性。（也就是我们说的，事务应该放到业务层来控制）
* 持久层（Mybatis）
    - 也就是我们是常说的 dao 层。负责数据持久化，包括数据层即数据库和数据访问层，数据库是对数据进行持久化的载体，数据访问层是业务层和持久层交互的接口，业务层需要通过数据访问层将数据持久化到数据库中。
    - 通俗的讲，持久层就是和数据库交互，对数据库表进行增删改查的。
    
#### 什么叫 Spring MVC

* SpringMVC 是一种基于 Java 的实现 MVC 设计模型的请求驱动类型的轻量级 Web 框架
    - 属于 Spring FrameWork 的后续产品，已经融合在 Spring Web Flow 里面。Spring 框架提供了构建 Web 应用程序的全功能 MVC 模块。
    - 使用 Spring 可插入的 MVC 架构，从而在使用 Spring 进行 WEB 开发时，可以选择使用 Spring 的 Spring MVC 框架或集成其他MVC开发框架，如Struts1(现在一般不用)，Struts2等。
* SpringMVC已经成为目前最主流的 MVC 框架之一
    - 随着 Spring 3.0 的发布，全面超越 Struts2，成为最优秀的 MVC 框架。 
    - 它通过一套注解，让一个简单的 Java 类成为处理请求的控制器，而无须实现任何接口。同时它还支持 RESTful 编程风格的请求。

#### Spring MVC 的优势
* 清晰的角色划分
    - 前端控制器（DispatcherServlet）
    - 请求到处理器映射（HandlerMapping）
    - 处理器适配器（HandlerAdapter）
    - 视图解析器（ViewResolver）                                                                                                            
    - 处理器或页面控制器（Controller）
    - 验证器（Validator）
    - 命令对象（Command 请求参数绑定到的对象就叫命令对象）
    - 表单对象（Form Object 提供给表单展示和提交到的对象就叫表单对象）
* 分工明确，而且扩展点相当灵活
    - 可以很容易扩展，虽然几乎不需要。
* 由于命令对象就是一个 POJO，无需继承框架特定 API，可以使用命令对象直接作为业务对象。
* 和 Spring 其他框架无缝集成，是其它 Web 框架所不具备的。
* 可适配，通过 HandlerAdapter 可以支持任意的类作为处理器。
* 可定制性，HandlerMapping、ViewResolver 等能够非常简单的定制。
* 功能强大的数据验证、格式化、绑定机制。
* 利用 Spring 提供的 Mock 对象能够非常简单的进行 Web 层单元测试。
* 本地化、主题的解析的支持，使我们更容易进行国际化和主题的切换。
* 强大的 JSP 标签库，使 JSP 编写更容易。

## Spring MVC 入门

#### 环境搭建与入门程序
* /SpringMVC/SpringMVCDemo01Introduce
``` xml
<!--  pom.xml  -->
<!--  1.新建 web 项目，会有默认依赖，加入新的依赖  -->
<!--  2.导入坐标  -->
<!-- https://mvnrepository.com/artifact/org.springframework/spring-web -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-web</artifactId>
    <version>5.2.11.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.11.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.11.RELEASE</version>
</dependency>
```
``` xml 
<!--  resource.META-INF.web.xml  -->
<!--  配置加载初始化和基础的 Servlet  -->
<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

    <!--  初始化参数，加载 spring 的配置文件  -->
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--  启动服务器时就创建此 Servlet  -->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```
``` xml
<!--  resource.springmvc.xml  -->
<!--  关于 spring ioc 等等的相关配置文件，注意 xml 的命名空间  -->

<!--  开启注解扫描  -->
<context:component-scan base-package="com.joush"></context:component-scan>

<!--  配置视图解析器对象  -->
<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!--  视图解析器的目录，方便 Spring MVC 帮助实现返回字符串自动跳转字符串所在页面  -->
    <property name="prefix" value="/WEB-INF/pages/"></property>
    <!--  页面的后缀名  -->
    <property name="suffix" value=".jsp"></property>
</bean>

<!--  开启 Spring MVC 框架的注解支持  -->
<mvc:annotation-driven/>
<!--    
    在SpringMVC的各个组件中，处理器映射器、处理器适配器、视图解析器称为 SpringMVC 的三大组件。
    使用<mvc:annotation-driven>自动加载 RequestMappingHandlerMapping（处理映射器）和 RequestMappingHandlerAdapter（处理适配器）
    可用在 SpringMVC.xml 配置文件中使用 <mvc:annotation-driven> 替代注解处理器和适配器的配置。
-->
```
* 启动服务器后做的一些事情
    - 启动服务器，加载配置文件
        - DispatcherServlet 对象创建
        - springmvc.xml 被加载
        - HelloController 创建对象
    - 发送请求，后台处理请求
        - 经过 dispatcherServlet 前端控制器，所有请求都会经过此 Servlet（指挥控制中心）
        - 请求找到 /hello，返回 success
        - 视图解析器找到 success.jsp 返回前端页面

* RequestMapping 注解
    - 代码讲解
    ``` java
    // com.joush.controller.HelloController.java
    
    @Controller
    @RequestMapping(path = "/user") // 此时的访问路径是 http://localhost:8080/SpringMVCDemo01Introduce/user/hello
    public class HelloController {
        /*
            RequestMapping 可以写在类上或者是方法上，如果写在类上，就可以实现模块化开发, 在跳转路径上就需要加上类上的路径 
        */
        @RequestMapping(path = "/hello") // 此时的访问路径是 http://localhost:8080/SpringMVCDemo01Introduce/hello
        public String sayHello(){
            System.out.println("Hello World");
            return "success";
        }
    }
    ```
    - 属性
        - value: 用于指定请求的 URL，它和 path 属性是一样的
        - method: 用于指定请求的方式
        - params: 用于指定限制请求参数的条件，支持简单的表达式，要求请求的参数的 key 和 value 必须和配置的一样
            ``` java
            params = {"accountName"}, // 表示请求参数必须有 accountName
            params = {"accountName = 111"}, // 表示请求参数必须有 accountName, 且值为 111
            params = {"money!100"}, // 表示请求参数中 money 的值不能为 100
            ```
        - header: 用于指定限制请求消息头的条件，即请求的请求头中必须包含的属性
        - 注意，以上四个属性只要出现 2 个以上，他们的关系是与的关系, 即所有条件必须同时满足
    ``` java
    @Controller
    public class HelloController {
    
        @RequestMapping(value = "/testRequestMapping", method = {RequestMethod.GET,RequestMethod.POST}, params = {"username=111"},headers = {"Accept"})
        public String testRequestMapping(){
            System.out.println("测试 RequestMapping 注解");
            return "success";
        }
    
    }
    ```