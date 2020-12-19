## Spring Boot 起步

#### Spring Boot 简介
* Spring 的优点
    - Spring是Java企业版（Java Enterprise Edition，JEE，也称J2EE）的轻量级代替品。 
    - 无需开发重量级的 Enterprise JavaBean（EJB），Spring为企业级Java开发提供了一种相对简单的方法，通过依赖注入和面向切面编程，用简单的Java对象（Plain Old Java Object，POJO）实现了EJB的功能。
* Spring 的缺点
    - 虽然 Spring 的组件代码是轻量级的，但它的配置却是重量级的。一开始，Spring 用 XML 配置，而且是很多XML配置。Spring 2.5 引入了基于注解的组件扫描，这消除了大量针对应用程序自身组件的显式XML配置。Spring 3.0 引入了基于 Java 的配置，这是一种类型安全的可重构配置方式，可以代替 XML。
    - 所有这些配置都代表了开发时的损耗。因为在思考 Spring 特性配置和解决业务问题之间需要进行思维切换，所以编写配置挤占了编写应用程序逻辑的时间。和所有框架一样，Spring 实用，但与此同时它要求的回报也不少。
    - 除此之外，项目的依赖管理也是一件耗时耗力的事情。在环境搭建时，需要分析要导入哪些库的坐标，而且还需要分析导入与之有依赖关系的其他库的坐标，一旦选错了依赖的版本，随之而来的不兼容问题就会严重阻碍项目的开发进度。

* Spring Boot 的特点
    - SpringBoot 对上述 Spring 的缺点进行的改善和优化，基于约定优于配置的思想，可以让开发人员不必在配置与逻辑业务之间进行思维的切换，全身心的投入到逻辑业务的代码编写中，从而大大提高了开发的效率，一定程度上缩短了项目周期。
    - 为基于 Spring 的开发提供更快的入门体验
    - 开箱即用，没有代码生成，也无需XML配置。同时也可以修改默认值来满足特定的需求
    - 提供了一些大型项目中常见的非功能性特性，如嵌入式服务器、安全、指标，健康检测、外部配置等
    - SpringBoot 不是对 Spring 功能上的增强，而是提供了一种快速使用 Spring 的方式
    
* Spring Boot 的核心功能
    - 起步依赖
    ``` text
    起步依赖本质上是一个Maven项目对象模型（Project Object Model，POM），定义了对其他库的传递依赖，这些东西加在一起即支持某项功能。
    简单的说，起步依赖就是将具备某种功能的坐标打包到一起，并提供一些默认的功能。
    ```
    - 自动配置
    ``` text
    Spring Boot的自动配置是一个运行时（更准确地说，是应用程序启动时）的过程，考虑了众多因素，才决定 Spring 配置应该用哪个，不该用哪个。该过程是 Spring 自动完成的。
    ```
#### Spring Boot 搭建环境

* 导入坐标
``` xml
<!--  pom.xml  -->
<!-- Spring Boot 要求，项目要继承 Spring Boot 的起步依赖 spring-boot-starter-parent -->
<project>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.5.RELEASE</version>
    </parent>
</project>

<!-- 同时 Spring Boot 要集成 SpringMVC 进行 Controller 的开发，所以项目导入 web 启动依赖 spring-boot-starter-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.3.5.RELEASE</version>
</dependency>
```

* 编写 Spring Boot 引导类
``` java
// com.joush.MySpringBootApplication.java

/*
    1.添加注解
    2.调用run方法
    3.传入本类的字节码文件
*/
@SpringBootApplication
public class MySpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class);
    }
}
```

* 编写 Controller 
``` java
// com.joush.controller.QuickController.java
/*
    1.类添加 @Controller 注解
    2.方法添加 @RequestMapping("/quick"), @ResponseBody 注解             
*/
@Controller
public class QuickController {
    @RequestMapping("/quick")
    @ResponseBody
    public String quick(){
        return "Hello Spring Boot";
    }
}
```

* 测试
    - 启动 MySpringBootApplication 的 main 方法
    - 浏览器访问 <http://localhost:8080/quick>
    - 查看到 Hello Spring Boot 即表示成功

#### Spring Boot 的小功能
* Spring Boot 自动热部署
    - 添加依赖，实现热部署，不需要重启服务
        ``` xml
        <!--  pom.xml  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <version>2.3.5.RELEASE</version>
        </dependency>
        ```
    - 修改 idea 的配置  
        `File - Settings - Build,Execution,Deployment - Compiler, 勾选 Build project automatically`
    - 按下  
        `Ctrl + Shift + Alt + / , 打开 Registry, 勾选 compiler.automake.allow.when.app.running`

* 使用 idea 快速创建 Spring Boot 工程
    ``` text
    创建完成后报错，右键该工程，Add Framework Support，选择maven，重新刷新maven即可
    ```
## Spring Boot 的配置文件
* 在 resources 下创建 `**/application*.properties`
``` properties
# resources.application.properties
# 在此处修改配置可以覆盖默认配置
# 配置默认端口号
server.port=8081

# 配置当前 web 应用的名称
server.servlet.context-path=/demo
```
#### Spring Boot 配置文件的类型

* SpringBoot 是基于约定的，所以很多配置都有默认值，但如果想使用自己的配置替换默认配置的话，就可以使用 application.properties 或者 application.yml（application.yaml）进行配置。
* SpringBoot 默认会从 Resources 目录下加载 application.properties 或 application.yml（application.yaml）文件
* application.properties 文件是键值对类型的文件，之前一直在使用，所以此处不在对 properties 文件的格式进行阐述。除了 properties 文件外，SpringBoot 还可以使用 yml 文件进行配置

#### yaml

* YML 文件格式是 YAML (YAML Ain't Markup Language) 编写的文件格式
  - YAML是一种直观的能够被电脑识别的的数据数据序列化格式
  - 容易被人类阅读，容易和脚本语言交互的，可以被支持YAML库的不同的编程语言程序导入
  - C/C++, Ruby, Python, Java, Perl, C#, PHP等。YML文件是以数据为核心的，比传统的xml方式更加简洁
* yaml 语法
```yaml
# 键: 值
# 注意 : 和 值之间的空格不能省略
# 同时以缩进确定层级关系，不限制缩进位数，但同一缩进代表同一层级

# 普通数据的配置
name: joush

# 对象的配置
person:
  name: joush
  age: 18
  addr: beijing

# 行内对象配置
person1: {name: joush, age: 18, addr: shanghai}

# 配置集合或数组（字符串）
city:
  - beijing
  - tianjin
  - shanghai
  - chongqing

# 行内方式
city2: [beijing,tianjin,chongqing,shanghai]

# 配置集合或数组（对象数据）
student:
  - name: tom
    age: 18
    addr: beijing
  - name: kite
    age: 20
    addr: shanghai

# 行内方式
  student2: [{name: tom, age: 18, addr: beijing},{name: kite, age: 20, addr: shanghai}]

# 配置 map 集合
map:
  key1: value1
  key2: value2
```

* 读取 yaml 文件的内容
    - 使用 `Value` 注解
    ```java
    // com.joush.controller.Quick2Controller.java
    @Controller
    public class Quick2Controller {
        // 使用 value 注解，自动注入
        @Value("${name}")
        private String name;
    
        @Value("${person.addr}")
        private String addr;
    
        @RequestMapping("/quick2")
        @ResponseBody
        public String quick2() {
    
            System.out.println(name); // joush2
            System.out.println(addr); // beijing
    
            return name + "," + addr;
        }
    }
    ```
    - 使用 `@ConfigurationProperties` 注解
    ```java
    // com.joush.controller.Quick3Controller.java
    @Controller
    @ConfigurationProperties(prefix = "person") // prefix 表示对象的前缀
    public class Quick3Controller {
        
        /*
            使用此方法需要提供 get 和 set 方法
        */
        private String name;
        private String addr;
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getAddr() {
            return addr;
        }
    
        public void setAddr(String addr) {
            this.addr = addr;
        }
    
        @RequestMapping("/quick3")
        @ResponseBody
        public String quick2(){
            System.out.println(name); // joush2
            System.out.println(addr); // beijing
    
            return name + "," + addr;
        }
    }
    ```

## Spring Boot 集成其他技术

#### Spring Boot 集成 Mybatis
* 添加依赖
```xml
<!--  pom.xml  -->
<!--  mybatis 起步依赖  -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.21</version>
</dependency>
```
* 添加数据库连接信息，配置 mybatis 包扫描
```properties
# resource.application.properties

# 数据库连接信息，注意变量名不能更改
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/joush?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=liyihang123

# 配置 mybatis 信息
# Spring 集成 Mybatis 环境

# pojo 别名扫描
mybatis.type-aliases-package=com.joush.domain

# 加载 Mybatis 映射文件
mybatis.mapper-locations=classpath:mapper/*Mapper.xml
```
* 编写 mapper
```java
// com.joush.mapper.UserMapper.java
@Mapper
public interface UserMapper {
    /*
        查询所有
     */
    List<User> queryUserList();

}
```
* 配置 mapper 映射文件
```xml
<!--  resources.mapper.UserMapper.xml  -->
<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.joush.mapper.UserMapper">
    <select id="queryUserList" resultType="user">
        select * from user
    </select>
</mapper>
```
* 测试
```java
// com.joush.controller.MybatisController.java
@Controller
@RequestMapping("/user")
public class MybatisController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/queryUserList")
    @ResponseBody
    public List<User> queryUserList(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
        return users;
    }
}
```

#### Spring Boot 集成 JUnit

* 起步依赖已经由自动化构建添加过了 `spring-boot-starter-test`

* 建立测试文件即可
```java
// com.joush.MybatisTest.java

import com.joush.domain.User;
import com.joush.mapper.UserMapper;
import org.junit.jupiter.api.Test; // 注意 test 的导包位置
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void mybatisTest(){
        List<User> users = userMapper.queryUserList();
        System.out.println(users);
    }
}
```

#### Spring Boot 集成 Spring Data JPA

#### Spring Boot 集成 Redis 
* 添加起步依赖
```xml
<!--  pom.xml  -->
<!--  Redis 起步依赖  -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
* 添加 redis 配置信息
```properties
# resources.application.properties
# redis 连接信息
spring.redis.host=127.0.0.1
spring.redis.port=6379
```
* 编写测试
```java
// com.joush.RedisTest.java
// 测试之前需先打开 redis 服务器
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // redis 模板对象

    @Autowired
    private UserMapper userMapper; // 数据库对象

    @Test
    public void redisTest() throws JsonProcessingException {

        // 1.从 redis 获取数据，数据形式为 json
        String userListJson = redisTemplate.boundValueOps("user.findAll").get(); // 获取数据

        // 2.判断 redis 是否存在该数据
        if (null == userListJson){
            // 3.不存在，去数据库查询
            List<User> users = userMapper.queryUserList();

            // 4.将查询的结果放入 json 格式，使用 jackson 转换
            ObjectMapper objectMapper = new ObjectMapper(); // jackson 转换 json
            userListJson = objectMapper.writeValueAsString(users);

            redisTemplate.boundValueOps("user.findAll").set(userListJson); // 写入 json 数据

            System.out.println("数据库中查询获得 User");
        } else {
            // 5.打印
            System.out.println("获取 redis 缓存");
        }

        System.out.println(userListJson);

    }

}
```
