# Spring Cloud

## introduce

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

#### 建 module
* 新建一个 module 项目
#### 改 pom
* 修改新的 module 项目的 pom 文件，引入依赖
* 父 pom 有的，不需要写版本号，没有的需要加入版本号
#### 写 yml
* 在新模块的 resource 下新建 `application.yml` 添加配置文件
```yaml
server:
  port: 8001
  
spring:
  application:
    name: cloud-payment-service
```
#### 主启动
* 在 java 下新建主启动类 `com.joush.springcloud.PaymentMain8001.java`
```java
@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class);
    }
}
```
#### 业务类
* 建表
* entities
* dao
* service
* controller