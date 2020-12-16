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
```javaproperties
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

