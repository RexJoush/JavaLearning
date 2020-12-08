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
<!--  resources.META-INF.web.xml  -->
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
<!--  resources.springmvc.xml  -->
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

## Spring MVC 的请求参数绑定

#### 请求参数的绑定说明

* 绑定机制
    - 表单提交的数据都是k=v格式的 username=haha&password=123
    - SpringMVC 的参数绑定过程是把表单提交的请求参数，作为控制器中方法的参数进行绑定的
    - 要求：提交表单的 name 和参数的名称是相同的
* 支持的数据类型
    - 基本数据类型和字符串类型
    - 实体类型（JavaBean）
    - 集合数据类型（List，Map 等）

* 基本数据类型和字符串类型
    - 提交表单的 name 和参数名称是相同的
    - 区分大小写
* 实体类型（JavaBean）
    - 提交表单的 name 和 JavaBean 中的属性名称需要一致
    - 如果一个 JavaBean 类中包含其他的引用类型，那么表单的 name 属性需要编写成：对象.属性 如：address.name
    ```java 
        // com.joush.domain.Account.java
        public class Account implements Serializable {
        
            private String username;
            private String password;
            private double money;
            
            private User user;
        }
        ```
    ``` html
        <!--  webapp.param.jsp  -->
        <!--  表单  -->
        <form action="param/saveAccount" method="post">
            姓名：<input type="text" name="username">
            密码：<input type="password" name="password">
            金额：<input type="text" name="money">
            
            <!--  实体类有引用成员变量，使用 对象名.属性名 进行封装  -->
            用户名：<input type="text" name="user.name">
            年龄：<input type="text" name="user.age">
            <input type="submit" value="提交">
        </form>
    ```
* 给集合属性数据封装
    - jsp 页面编写方式：list[0].属性
    ```java 
    // com.joush.domain.Account.java
    public class Account implements Serializable {
    
        private String username;
        private String password;
        private double money;
    
        private List<User> list;
        private Map<String, User> map;
    }
    ```
    ``` html
    <!--  webapp.param.jsp  -->
    <form action="param/saveAccount" method="post"><br>
        
        姓名：<input type="text" name="username"><br>
        密码：<input type="password" name="password"><br>
        金额：<input type="text" name="money"><br>

        <!--  封装到 User 对象中，将此对象封装到 list 中
                list[0].attribute1
                list[0].attribute2
                成员集合的名字[下标值].集合元素的属性名
          -->
        用户名1：<input type="text" name="list[0].name"><br>
        年龄1：<input type="text" name="list.[0].age"><br>

        <!--  封装到 User 对象中，将此对象封装到 map 中
                map['key'].attribute1
                map['key'].attribute2
                成员集合的名字['键值'].集合元素的属性名
          -->
        用户名2：<input type="text" name="map['one'].name"><br>
        年龄2：<input type="text" name="map['one'].age"><br>
        <input type="submit" value="提交"><br>
    </form>
    ```
* 请求参数中文乱码的解决
``` xml
<!--  webapp/WEB-INF/web.xml  -->
<!--  配置 Spring 提供的过滤器类，解决中文乱码问题  -->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <!-- 初始化参数 -->
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

* 自定义类型转换器
    - 实现 org.springframework.core.convert.converter.Converter 接口
    ``` java
    // com.joush.utils.StringToDateConverter.java
    //                                                      转换的泛型
    public class StringToDateConverter implements Converter<String, Date> {
    
        /**
         * @param s 传入的字符串
         * @return 转换后的日期
         */
        @Override
        public Date convert(String s) {
            if (s == null){
                throw new RuntimeException("please input date string.");
            }
            // 使用 java.text.DateFormat 对象进行日期的格式化
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 把字符串转换日期
            try {
                return dateFormat.parse(s);
            } catch (ParseException e) {
                throw new RuntimeException("data type transfer error");
            }
        }
    }
    ```
    - 配置 spring mvc 的类型转换器
    ``` xml
    <!--  resources.springmvc.xml  -->
    
    <!--  配置自定义类型转换器  -->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.joush.utils.StringToDateConverter"/>
            </set>
        </property>
    </bean>
    
    <!--  开启 Spring MVC 框架的注解支持，同时支持类型转换器  -->
    <mvc:annotation-driven conversion-service="conversionService"/>
    ```
* 获取原生的 Servlet Api
``` java
// com.joush.controller.ParamController.java
/**
 * 获取原生的 servlet api
 * @return
 */
@RequestMapping("/testServlet")
//                          获取谁即可直接在请求方法的参数列表中加入参数即可
public String testServlet(HttpServletRequest request, HttpServletResponse response){

    System.out.println(request);

    HttpSession session = request.getSession();
    System.out.println(session);
    
    ServletContext servletContext = session.getServletContext();
    System.out.println(servletContext);

    System.out.println(response);

    return "success";
}
```

#### Spring MVC 的注解

* RequestParams 注解
    - 作用，把请求中指定名称的参数给控制器中的形参赋值
    - 属性
        - value: 请求参数中的名称
        - required: 请求参数是否必须提供此参数，默认值 true，表示必须提供，不提供将报错
    - 使用介绍
        ``` html
        <!--  webapp.annotation.jsp  -->
        <!--  常用注解  --!>
        <a href="annotation/testRequestParam?name=haha">RequestParam</a><br>
        ```
        ``` java
        // com.joush.controller.AnnotationController.java
        
        @RequestMapping("/testRequestParam")
                                        // 不加此注解，则参数名和前端属性名要一样，家里此注解，前端属性名需要和注解一样，默认 required，如果不传，则报 400 错误
        public String testRequestParam(@RequestParam(name = "name") String username){
            System.out.println(username);
            return "success";
        }
        ```
* RequestBody 注解
    - 作用，用于获取请求体中的内容，直接使用得到的是 key=value&key=value... 结构的数据，get 请求不适用
    - 属性
        - required: 请求参数是否必须提供此参数，默认值 true，取值为 true 时，get 请求会保存，如果取值为 false，get 请求得到的是 null
    - 使用介绍
    ``` html
    <!--  webapp.annotation.jsp  -->
    <!--  常用注解  --!>
    <form action="annotation/testRequestBody" method="post"><br>
        姓名：<input type="text" name="username"><br>
        年龄：<input type="text" name="age"><br>
    
        <input type="submit" value="提交"><br>
    </form>
    ```
    ``` java
    // com.joush.controller.AnnotationController.java
    
    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String body){
        System.out.println(body); // username=RexJoush&age=12
        return "success";
    }
    ```
  
* PathVariable 注解
    - 作用，用于绑定 url 中的占位符，例如：请求 url 中 /delete/{id}, 这个 {id} 就是占位符，url 支持的占位符是 spring 3.0 后加入的，是 springmvc 支持 rest 风格 url 的一个重要标志
    - 属性
        - value: 用于指定 url 中占位符的名称
        - required: 是否必须提供占位符

    - 使用介绍
    ``` java
    // com.joush.controller.AnnotationController.java
    
    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String body){
        System.out.println(body); // username=RexJoush&age=12
        return "success";
    }
    ```
* RequestHeader 注解
    - 作用，用于获取请求头消息
    - 属性
        - value: 提供消息头名称
        - required: 是否必须有此消息头
        - 实际开发一般不用
    - 使用介绍
    ``` java
    // com.joush.controller.AnnotationController.java
    
    @RequestMapping("/testRequestHeader")
    //                              写出要获取头的名称即可
    public String testRequestHeader(@RequestHeader("accept") String header){
        System.out.println(header);
        return "success";
    }
    ```
* CookieValue 注解
    - 作用，用于把指定 cookie 名称的值传入控制器方法参数
    - 属性
        - value: 指定 cookie 名称
        - required: 是否必须有此 cookie
    - 使用介绍
    ``` java
    // com.joush.controller.AnnotationController.java
    
    @RequestMapping("/testRequestHeader")
    //                              写出要获取头的名称即可
    public String testRequestHeader(@RequestHeader("accept") String header){
        System.out.println(header);
        return "success";
    }
    ```
* ModelAttribute 注解
    - 作用，该注解是 SpringMVC 4.3 版本以后新加入的。它可以用于修饰方法和参数
        - 出现在方法上，表示当前方法会在控制器的方法执行之前，先执行。它可以修饰没有返回值的方法，也可以修饰有具体返回值的方法
        - 出现在参数上，获取指定的数据给参数赋值
    - 属性
        - value: 用于获取数据的 key。key 可以是 POJO 的属性名称，也可以是 map 结构的 key
    - 使用介绍
        - 使用场景，当表单提交数据不是完整的实体类数据时，保证没有提交数据的字段使用数据库对象原来的数据
    ``` java
    // com.joush.controller.AnnotationController.java
    
    /*
        带返回值的，将传送过来的 pojo 对象进行封装后，返回此对象
    */
    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(User user) {
        System.out.println("testModelAttribute executed!");
        System.out.println(user);
        return "success";
    }
  
    /**
     * 该方法会先执行
     */
    @ModelAttribute
    public User showUser(String name){
        System.out.println("show user executed!");
    
        // 通过用户名查询数据库（模拟）
        User user = new User();
        user.setName(name);
        user.setAge(20);
        user.setDate(new Date());
    
        return user;
    }
    
    /*
        不带返回值的，将传送过来的 pojo 对象放入一个 map 集合，在方法中通过 key 获取此对象
    */

    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("abc") User user) {
        System.out.println("testModelAttribute executed!");
        System.out.println(user);
        return "success";
    }

    @ModelAttribute
    public void showUser(String name, Map<String, User> map){
        System.out.println("show user executed!");

        User user = new User();
        user.setName(name);
        user.setAge(20);
        user.setDate(new Date());

        map.put("abc", user);

    }
    ```
* SessionAttribute 注解
    - 作用，用于多次执行控制器方法间的数据共享
    - 属性
        - value: 指定指定存入的属性名称
        - type: 用于指定存入的数据类型
    - 使用介绍
    ``` java
    // com.joush.controller.AnnotationController.java
    
    @RequestMapping("/testRequestHeader")
    //                              写出要获取头的名称即可
    public String testRequestHeader(@RequestHeader("accept") String header){
        System.out.println(header);
        return "success";
    }
    ```

#### REST 风格 URL
* 什么是 rest
    - REST（Representational State Transfer，表述性状态传递）描述了一个架构样式的网络系统，比如 web 应用程序
    - 它首次出现在 2000 年 Roy Fielding 的博士论文中，他是 HTTP 规范的主要编写者之一
    - 在目前主流的三种 Web 服务交互方案中，REST 相比于 SOAP（Simple Object Access protocol，简单对象访问协议）以及 XML-RPC 更加简单明了，无论是对 URL 的处理还是对 Payload 的编码，REST 都倾向于用更加简单轻量的方法设计和实现
    - 值得注意的是 REST 并没有一个明确的标准，而更像是一种设计的风格。它本身并没有什么实用性，其核心价值在于如何设计出符合 REST 风格的网络接口
* restful 的优点
    - 它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用
* restful 的特性
    - 资源（Resources），网络上的一个实体，或者说是网络上的一个具体信息
        - 它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的存在。可以用一个URI（统一资源定位符）指向它，每种资源对应一个特定的 URI
        - 要获取这个资源，访问它的URI就可以，因此 URI 即为每一个资源的独一无二的识别符
    - 表现层（Representation），把资源具体呈现出来的形式，叫做它的表现层
        - 比如，文本可以用 txt 格式表现，也可以用 HTML 格式、XML 格式、JSON 格式表现，甚至可以采用二进制格式。
    - 状态转化（State Transfer），每发出一个请求，就代表了客户端和服务器的一次交互过程
        - HTTP 协议，是一个无状态协议，即所有的状态都保存在服务器端。因此，如果客户端想要操作服务器，必须通过某种手段，让服务器端发生“状态转化”（State Transfer）
        - 这种转化是建立在表现层之上的，所以就是 “表现层状态转化”
        - HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源
        ``` text 
        restful的示例： 
        /account/1 HTTP GET ： 得到 id = 1 的 account 
        /account/1 HTTP DELETE： 删除 id = 1的 account 
        /account/1 HTTP PUT： 更新id = 1的 account
        /account HTTP POST： 新增 account
        ```
      
## Spring MVC 的响应数据和结果视图

#### 返回值分类

* String
``` java
// com.joush.controler.UserController.java
/**
 * 返回值类型是 String, 此时，视图解析器会查找名字会返回值的字符串的页面，即 success.jsp
 * @param model
 * @return
 */
@RequestMapping("/testString")
public String testString(Model model){
    System.out.println("testString executed!");

    // 模拟从数据库中查询出 User 对象

    User user = new User();
    user.setUsername("joush");
    user.setPassword("123");
    user.setAge(20);

    // model 对象
    model.addAttribute("user", user);

    return "success";
}
```
* void
``` java
/**
 * 返回值类型是 void，此时视图解析器回去找和请求路径名称相同的页面，即 testVoid.jsp
 * 有三种解决方法
 * @param model
 * @return
 */
@RequestMapping("/testVoid")
public void testVoid(Model model){
    
    System.out.println("testVoid executed!");
    
    // 1.可以编写请求转发的程序
    request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request, response);

    // 2.也可以重定向
    response.sendRedirect(request.getContextPath() + "/index.jsp");

    // 3.直接进行响应
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=utf8");
    response.getWriter().println("你好");
}
```
* ModelAndView 对象
    - ModelAndView 是 SpringMVC 为我们提供的一个对象，该对象也可以用作控制器方法的返回值
    ``` java
    /**
     * 返回 ModelAndView 对象
     * @return
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        System.out.println("testModelAndView executed!");

        // 创建 ModelAndView 对象
        ModelAndView mv = new ModelAndView();

        // 模拟从数据库中查询出 User 对象
        User user = new User();
        user.setUsername("joush");
        user.setPassword("123");
        user.setAge(20);

        // 把 user 对象存入 mv 中，也会把 user 对象存入 request 中
        mv.addObject("user", user);

        // 跳转到哪个页面，通过视图解析器完成
        mv.setViewName("success");

        return mv;
    }
    ```

#### 转发和重定向

* forward 转发 或 重定向
``` java
/**
 * 使用关键字来实现转发或重定向
 * @return
 */
@RequestMapping("/testForwardOrRedirect")
public String testForwardOrRedirect(){

    System.out.println("testForwardOrRedirect executed!");

    // 请求转发
    return "forward:/WEB-INF/pages/success.jsp";

    // 重定向，正常的重定向需要加上 request.getContextPath()，此处 spring mvc 已经默认加上了，所以不需要加
    return "redirect:/index.jsp";
}
```

#### ResponseBody 响应 json 数据

* 配置前端控制器，不拦截静态资源
``` xml
<!--  resource.springmvc.xml  -->
<!--  前端控制器，配置哪些资源不拦截，即 js，css，images 等等  -->
<mvc:resources mapping="/js/**" location="/js/"></mvc:resources>
```
* 添加依赖，由 spring mvc 进行 json 的解析
``` xml
<!--  pom.xml  -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.11.3</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.11.3</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.11.3</version>
</dependency>
```
* 添加注解，由 spring mvc 进行解析
``` java
// com.joush.controller.UserController.java
@RequestMapping("/testAjax")
//      返回的是 user 的 json 格式串        // 将获取到的 json 格式的 body 数据解析封装成 user 对象
public @ResponseBody User testAjax(@RequestBody User user){
    System.out.println("testAjax executed!");

    // 客户端发送 ajax 请求，传的是 json 字符串，后端把 json 字符串封装到 user 对象中
    System.out.println(user);

    // 做响应
    user.setUsername("haha");
    user.setAge(30);

    return user;
}
```

## 文件上传

#### 文件上传回顾

* 文件上传的必要前提
    - form 表单的 enctype 取值必须是 multipart/form-data（默认值是 application/x-www-form-urlencoded，enctype 是表单请求正文的类型）
    - method 属性取值必须是 post
    - 提供一个文件选择域 `<input type="file" />`
* 文件上传的原理分析
``` 
    当 form 表单的 enctype 取值不是默认值后，request.getParameter() 将失效
    enctype="application/x-www-form-urlencoded" 时，form 表单的正文内容是
        key=value&key=value&key=value
    当 form 表单的 enctype 取值为 multipart/form-data 时，请求正文内容就变成
        每一部分都是 MIME 类型描述的正文
            ----------------------------------7de1a433602ac  分界符
            Content-Disposition: form-data; name="userName"  协议头
            aaa                                              协议正文
            ----------------------------------7de1a433602ac
            Content-Disposition:form-data;name="file";filename="C:\x\x.txt"
            Content-Type: text/plain                         协议类型（MIME 类型） 
            ----------------------------------7de1a433602ac
```
* 借助第三方组件实现上传
    - commons-fileupload-x.x.x.jar
    - commons-io-x.x.jar

#### 传统