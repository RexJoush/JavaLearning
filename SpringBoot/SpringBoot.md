## Spring Boot 起步

#### Spring Boot 搭建环境
* Spring Boot 要求，项目要继承 Spring Boot 的起步依赖 spring-boot-starter-parent
``` xml
<!--  pom.xml  -->
<project>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.5.RELEASE</version>
    </parent>
</project>
```

* 同时 Spring Boot 要集成 SpringMVC 进行 Controller 的开发，所以项目导入 web 启动依赖 spring-boot-starter-web
``` xml
<!--  pom.xml  -->
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

        