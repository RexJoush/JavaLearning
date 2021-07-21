# 1.Springboot

#### 前置知识

* spring，spring mvc 基础
* maven 基础

#### 环境要求

* jdk 1.8+
* maven 3.3+
* tomcat 9.0+

## Hello world

#### 新建 maven 工程

#### 导入 `pom.xml`

```xml
<!-- 引入父工程  -->
<modelVersion>4.0.0</modelVersion>
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.3</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<!-- 添加 spring boot web 场景启动器  -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

#### 新建主启动类

```java
@SpringBootApplication // 主启动配置
public class MySpringBootApplication {
    public static void main(String[] args) {
        // 启动
        SpringApplication.run(MySpringBootApplication.class);
    }
}
```

#### 新建controller

```java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rex Joush
 * @time 2021.04.26 13:44
 */
@Controller
@ResponseBody
/*
ResponseBody 注解是为了让返回的字符串直接返回页面，而不用模板引擎解释，所以加上此注解，spring boot提供了一个新的 RestController 注解合并了 Controller 和 ResponseBody 
*/
@RestController
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring boot";
    }
}
```

#### 主配置文件

* [Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties)

```yaml
# resource application.yml
server:
  port: 8081
```

#### 便捷部署

* 添加编译组件

```xml
<!--  添加打包方式  -->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <version>2.4.3</version>
        </plugin>
    </plugins>
</build>
```

* 执行 maven clean install
* 运行 jar 包

```shell
$ java -jar boot-01-helloword-1.0-SNAPSHOT.jar
```

## 依赖管理

[场景启动器](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter)

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.4.RELEASE</version>
</parent>
<!-- 几乎声明了所有开发中常用的依赖的版本号,自动版本仲裁机制 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.3.4.RELEASE</version>
</parent>
```

# 2.容器功能

## 配置绑定

* 如何使用Java读取到properties文件中的内容，并且把它封装到JavaBean中，以供随时使用

```java
// 配置注入组件
@Component
@ConfigurationProperties(prefix = "mycar")
public class Car {

    private String bound;
    private int price;

    @Override
    public String toString() {
        return "Car{" +
                "bound='" + bound + '\'' +
                ", price=" + price +
                '}';
    }

    public String getBound() {
        return bound;
    }

    public void setBound(String bound) {
        this.bound = bound;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

// 在任意一个配置类中添加此注解
@Configuration
@EnableConfigurationProperties(Car.class) // 适用于第三方类，无法注入容器时使用此方法
public class MyConfig {

    @Bean
    public User user1(){
        return new User("joush", 23);
    }

    @Bean("tom")
    public Pat pat1(){
        return new Pat("tom");
    }
}

// properties 的使用
@RequestMapping("/car")
public Car car(){
    System.out.println(car); // BYD, 10
    return car;
}
```

```yaml
# application.yml
mycar:
  bound: BYD
  price: 10
```

## lombok

* 简化 bean 开发

* 引入依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

* 安装 lombok 插件
    - settings - plugins - lombok
    - 重启 idea

# 3.Web

## 简单功能

#### 静态资源访问

* 静态资源目录，类路径下

    * 默认路径

        * `/static`
        * `/public`
        * `/resource`
        * `/META-INF/resources`

    * 修改路径

        ```yml
        # applicat.yml
        spring:
          web:
            resources:
              static-locations: [classpath:/haha/]
        ```

* 访问，静态映射/**

* 静态资源访问前缀

    ```yml
    # applicat.yml
    spring:
      mvc:
        static-path-pattern: /res/**
    ```

* 当有controller 和静态资源路径一样时，先访问controller，在访问静态资源

* resources 下的目录结构

```text
resources|
         |--META-INF|
         |          |--resources
         |--haha|
         |      |--haha.txt
         |--public
         |      |--b.txt
         |--resources
         |      |--c.txt
         |--static
         |      |--d.txt
         |      |--index.html
         |--application.yml
```

* 访问

    * `http://localhost:8080/a.txt`
    * `http://localhost:8080/b.txt`
    * `http://localhost:8080/c.txt`
    * `http://localhost:8080/d.txt`

## 请求参数处理

#### 请求参数处理

* Rest 使用与原理

* Rest 风格支持

    * 以前  	/getUser 获取用户	/deleteUser 删除用户 	/editUser 编辑用户		/saveUser 保存用户

    * 现在     /user    GET 获取用户    DELETE 删除用户    PUT 修改用户    POST 保存用户

    * 核心 filter

        * 用法：表单 method=post，隐藏域 `_method=put`
            ```html
            <!--  index.html  -->
            <html>
            <head>
                <title>Test</title>
                <meta charset="UTF-8">
            </head>
            <body>
            <h2>Test Rest</h2>
            <form action="/user" method="get">
                <input type="submit" value="Rest-Get"/>
            </form>
            <form action="/user" method="post">
                <input type="submit" value="Rest-Post"/>
            </form>
            <form action="/user" method="post">
                <input type="hidden" value="delete" name="_method">
                <input type="submit" value="Rest-Delete"/>
            </form>
            <form action="/user" method="post">
                <input type="hidden" value="put" name="_method">
                <input type="submit" value="Rest-Put"/>
            </form>
            </body>
            </html>
            ```
        * sptingboot 手动开启rest支持
            ```yml
            spring:
              mvc:
                hiddenmethod:
                  filter:
                    enabled: true
            ```
* rest 原理

    * 表单提交会带上 `_method=PUT`
    * 请求过来被 HiddenHttpMethodFilter 拦截
        * 请求是否正常，并且是POST
            * 获取到_method的值
            * 兼容以下请求
                * PUT
                * DELETE
                * OPTION
            * 原生 request(post) 包装模式 requestWrpper 重写了 getMethod 方法，返回是传入的值，此处用到了装饰者模式
            * 过滤器链放行的时候用 wrapper，以后的方法调用 getMethod 是调用 requestWrapper

#### 普通参数与基本注解
* 注解

    * @PathVariable （路径变量）
        ```java
        @GetMapping("/car/{id}/owner/{username}")
        public Map<String, Object> getCar(
            // 拿到id
            @PathVariable("id") int id,
            // 拿到 username
            @PathVariable("username") String name,
            // 当参数为 Map<String, String> 时，可以封装所有的参数
            @PathVariable Map<String, String> pv,
            // 获取请求头
            @RequestHeader("User-Agent") String userAgent){}
        ```

    * @RequestHeader （获取请求头）
        ```java
        @GetMapping("/car/{id}/owner/{username}")
        public Map<String, Object> getCar(
            // 获取特定请求头
            @RequestHeader("User-Agent") String userAgent,
            // 获取所有请求头
        	@RequestHeader Map<String, String> headers){}
        ```

    * @ModeAttribute 

    * @RequestParam （获取请求参数）
        ```java
        @GetMapping("/car/{id}/owner/{username}")
            public Map<String, Object> getCar(
                // url: car/3/owner/lee?age=20&interests=football&interests=sing
        		// 获取get请求的特定参数
                @RequestParam("age") int age,
                // 获取列表类型的参数
                @RequestParam("interests") List<String> interests,
                // 获取所有参数
                @RequestParam Map<String, String> params){}
        ```

    * @MatrixVariable （矩阵变量）
        ```HTML
        <a href="/cars/sell;low=34;brand=byd,audi,yadi">@MatrixVariable</a>
        ```

    * @CookieValue （获取cookie 值）
        ```java
        @GetMapping("/car/{id}/owner/{username}")
        public Map<String, Object> getCar(
        // 获取特定 cookie 值
            @CookieValue("token") String token){}
        ```
        
    * @RequestBody  （获取请求体）
        ```html
        <form action="/save" method="post">
                username: <input type="text" name="username">
            email: <input type="text" name="email">
                <input type="submit" value="submit">
        </form>
        ```
        ```java
        @PostMapping("/save")
        public Map<String, Object> getCar(
            // 获取特定 cookie 值
            @CookieValue("token") String token){}
        ```


#### 参数绑定

* 前端传递整个表单数据

* 前端 form 表单

    ```html
    <form action="/saveuser" method="post">
        姓名: <input type="text" name="userName" value="zhangsan"><br>
        年龄: <input type="text" name="age" value="18"><br>
        生日: <input type="text" name="birth" value="2020-04-18"><br>
        宠物姓名: <input type="text" name="pet.name" value="阿猫"><br>
        宠物年龄: <input type="text" name="pet.age" value="5"><br>
        <input type="submit" value="保存">
    </form>
    ```

* 后端接收参数

    ```java
    /**
     * 数据绑定：页面提交的请求数据（GET, POST）都可以和对象属性进行绑定
     * @param person
     * @return
     */
    @PostMapping("/saveuser")
    public String saveUser(Person person) {
        return person.toString();
    }
    ```

## 请求响应

#### 响应 JSON

* spring-boot-starter-web 已经引入了 json 支持，所以在方法上添加 @ResoinseBody 注解，后端自动给前端返回JSON数据
* 使用 RestController 注解即可

## 拦截器

#### HandlerInterceptor 接口

* 添加拦截器类，实现 HandlerInterceptor 接口

    ```java
    /**
     * 登录检查
     *
     * 1.配置拦截器拦截的请求
     * 2.把配置放在容器中
     */
    public class LoginInterceptor implements HandlerInterceptor {
    
        /**
         * 目标方法执行之前
         * @param request
         * @param response
         * @param handler
         * @return
         * @throws Exception
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
            HttpSession session = request.getSession();
    
            Object loginUser = session.getAttribute("loginUser");
    
            if (loginUser != null){
                //  放行
                return true;
            }
    
            // 否则，拦下来
            session.setAttribute("msg", "请先登录");
            response.sendRedirect("/");
            return false;
        }
    
        /**
         * 目标方法执行完成以后
         * @param request
         * @param response
         * @param handler
         * @param modelAndView
         * @throws Exception
         */
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        }
    
        /**
         * 页面渲染之后
         * @param request
         * @param response
         * @param handler
         * @param ex
         * @throws Exception
         */
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        }
    }
    ```

* 注册拦截器，添加配置项

    ```java
    /**
     * 需要实现 WebMvcConfigurer 接口 配置 mvc 的配置项
     */
    @Configuration
    public class AdminWebConfig implements WebMvcConfigurer {
    
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
    
            // 添加拦截器
            registry.addInterceptor(new LoginInterceptor())
                    .addPathPatterns("/**")     // 拦截所有 // 此处静态资源也会被拦截
                    .excludePathPatterns("/login","/", "/css/**"); // 放行登录, 并且放行静态资源
        }
    }
    ```

    

## 文件上传

#### 建立表单

```html
<form action="/upload" method="post" enctype="multipart/form-data">
  email:<input type="email" name="email"><br>	<!-- 普通属性 -->
  name:<input type="text" name="name"><br>		<!-- 普通属性 -->
  头像:<input type="file" name="image"><br>	   <!-- 单文件上传 -->
  生活照:<input type="file" name="photos" multiple><br> <!-- 多文件上传 -->
  <button type="submit">Submit</button>
</form>
```

#### controller层

```java
/**
     * MultipartFile 自动封装上传过来的文件
     *
     * @param email 普通属性，email
     * @param name  普通属性，name
     * @param image 单文件
     * @param photos 多文件
     * @return 上传结果
     */
@PostMapping("/upload")
public String upload(@RequestParam("email") String email,   // 普通属性
                     @RequestParam("name") String name,
                     @RequestPart("image") MultipartFile image, // 单文件
                     @RequestPart("photos") MultipartFile[] photos // 多文件
                    ) throws IOException {

    log.info("上传信息, email={}, name={}, image={}, photos={}",
             email, name, image.getSize(), photos.length);

    // 如果头像不为空，存储
    if (!image.isEmpty()) {
        // 保存到文件服务器，或者 OOM 对象存储服务器
        String originalFilename = image.getOriginalFilename();
        image.transferTo(new File("D:\\" + originalFilename));
    }

    // 存储多文件
    if (photos.length > 0) {
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()){
                String filename = photo.getOriginalFilename();
                photo.transferTo(new File("D:\\cache\\" + filename));
            }
        }
    }

    return "file upload success";
}
```

## Web 原生组件注入（Servlet，Filter，Listener）

#### 使用 Servlet api

* 添加 @ServletComponentScan 注解

    ```java
    @ServletComponentScan(basePackages = "com.joush.servlet")
    @SpringBootApplication
    public class Boot03InterceptorApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(Boot03InterceptorApplication.class, args);
        }
    
    }
    ```

    

# 4.数据访问

## JdbcTemplate，Druid

#### 数据源的自动配置

* 导入依赖

    ```xml
    <!--  导入启动器   -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <!--  导入 mysql 驱动   -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    ```

* JDBC 场景启动器导入信息
    * 数据源
    * jdbc
    * 事务处理
    * 但没有导入驱动，因为不知道导入什么数据库驱动，所以需要自己导入
* 数据库场景的自动配置分析
    * DataSourceTransactionManagerAutoConfiguration: 事务管理的自动配置
    * JdbcTemplateAutoConfiguration: JdbcTemplate的自动配置，可以来对数据库进行 crud
        * 可以修改这个配置项`@ConfigurationProperties(prefix="spring.jdbc")`来修改 JdbcTemplate
    * JndiDataSourceAutoConfiguration: jndi 的自动配置
    * XADataSourceAutoConfiguration: 分布式事务相关

#### 使用 Druid 数据源

* 导入依赖

    ```xml
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>1.2.6</version>
    </dependency>
    ```

* 配置自定义数据源

    ```java
    @Configuration
    public class MyDataSourceConfig {
        
        // 默认的自动配置是判断容器中没有数据源才会配 @ConditionalOnMissingBean(DataSource.class)
        @ConfigurationProperties("spring.datasource") // 配置数据源的各项属性
        @Bean // 注入数据源
        public DataSource dataSource(){
    
            DruidDataSource dataSource = new DruidDataSource();
    
            // 不需要设置，使用 Properties 配置即可
    //        dataSource.setUrl();
    
            return dataSource;
    
        }
    
    }
    ```

* 使用官方启动器 starter

    * 导入依赖

        ```xml
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.17</version>
        </dependency>
        ```

    * 配置示例

        ```yml
        spring:
          datasource:
            url:
            username:
            password:
            driver-class-name:
            
            durid:
              filter: stat, wall # 配置监控防火墙
              start-view-servlet:
                enabled: true # 开启监控功能
                login-username: admin # 监控页用户名
                login-password: admin # 监控页密码
                resetEnable: true # 重置按钮
              web-stat-filter:
              	enabled: true
              	urlPattern: /* # url 拦截地址
              	exclusions: '*.js,*.css，*.gif,*.jpg,*.png,*.ico,/druid/*' # url 放行
        ```

        

## Mybatis

### 配置模式

* 全局配置文件
* SqlSessionFactory：自动配置好了
* SqlSession： 自动配置了 SqlSessionTemplate 组合了 SqlSession
* @Import(AutoConfiguredMapperScannerRegister.class)
* Mapper: 只要我们写的操作 MyBatis 的接口标注了 @Mapper 就会被自动扫描进来

#### 使用过程

* 导入 mybatis 官方启动器
* 编写 mapper 接口
* 编写 sql 映射文件并绑定 mapper 接口
* 在 application.yml 中指定 Mapper 文件的位置

### 注解模式

* 在 Mapper 接口类方法上添加注解 sql 即可

    ```java
    @Mapper
    public interface CityMapper {
    
        @Select("select * from city where id = #{cityId}")
        City getById(int cityId);
    
    }
    ```

## Mybatis Plus

#### 导入坐标

```xml
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.2</version>
</dependency>
```

#### 自动配置

* MybatisPlusAutoConfiguration 配置类，MybatisPlusProperties 配置项绑定。mybatis-plus: xxx就是对 mybatis-plus 的定制
* SqlSessionFactory 自动配置好，底层容器是默认数据源
* mapperLocations 自动配置好的。有默认值。classpath\*:/mapper/**/\*.xml；任意包的类路径下的所有mapper文件夹下任意路径下的所有xml都是sql映射文件。  建议以后sql映射文件，放在 mapper下
* 容器中也自动配置好了SqlSessionTemplate
* @Mapper 标注的接口也会被自动扫描

#### 优点

* 继承 Base 即可拥有增删改查方法

    ```java
    // com.joush.mapper.UserMapper.java
    @Mapper
    public interface UserMapper extends BaseMapper<User> {
        
    }
    ```

* mybatis plus 要求实体类对应的属性在表中必须都存在，如果想要不存在，可以在属性上添加注解

    ```java
    @Data
    public class User {
        // 表示此属性不在数据库中存在，不封装
    	@TableField(exist = false)
        private String userName;
    
        private int id;
        private String name;
        private Integer age;
        private String email;
    }
    ```

* mybatis plus 默认会寻找与实体类相同名字的表名，如果表名不同，需要在实体类上显式标出

    ```java
    @Data
    @TableName("user")
    public class User {
        private int id;
        private String name;
        private Integer age;
        private String email;
    }
    ```

#### 使用

* Mapper 继承 BaseMapper<T>，完成 Mapper 层的增删改查

    ```java
    @Mapper
    public interface UserMapper extends BaseMapper<User> {
    
    }
    ```

* Service 接口继承 IService<T>类

    ```java
    public interface UserService extends IService<User> {
    }
    ```

* Service 实现类继承 ServiceImpl<Mapper, T> 实现 Service 层接口

    ```java
    @Service
    public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    }
    ```

#### Mybatis Plus 实现分页

* 使用 Page 对象

    ```java
    @GetMapping("/users")
    public Map<String, Object> getUsers(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage){
    	
        Map<String, Object> result = new HashMap<>();
    
        // 获取 Page 对象，第一个参数是当前页，第二个参数是页面大小
        Page<User> userPage = new Page<>(currentPage, 3);
        
        // 获取查询结果，第一个参数是page对象，第二个参数是查询条件
        Page<User> page = userService.page(userPage, null);
    
        result.put("code", 1200);
        result.put("message", "获取用户成功");
        result.put("data", page);
    
        return result;
    }
    ```

* 要想使用，需要设置分页插件

    ```java
    // 新建配置类，注入分页插件组件即可
    @Configuration
    public class MybatisConfig {
    
        @Bean
        public MybatisPlusInterceptor mybatisPlusInterceptor() {
            // 新建 mybatis plus 分页拦截器
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    
            // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
            // paginationInterceptor.setOverflow(false);
            // 设置最大单页限制数量，默认 500 条，-1 不受限制
            // paginationInterceptor.setLimit(500);
            // 开启 count 的 join 优化,只针对部分 left join
    
            // 添加分页拦截器
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
            return interceptor;
        }
    
    }
    ```

#### CRUD

* 查询

    ```java
    
    @GetMapping("/users")
    public String getUsers(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage){
    
    
        // 获取 Page 对象
        Page<User> userPage = new Page<>(currentPage, 3);
        Page<User> page = userService.page(userPage, null);
    	
        // 此处基本使用 page 方法
    	List<User> list = userService.list();
    
    	// 通过 id 获取
    	userService.getById(id);
        
        return Result.ok("获取列表成功", page);
    }
    ```

* 增加

    ```java
    // 添加用户
    @PostMapping("/users")
    public String addUser(@RequestBody User user){
        if (userService.save(user)){
            return Result.ok("添加用户成功");
        } else {
            return Result.error("添加用户失败");
        }
    }
    ```

* 删除

    ```java
    // 删除用户
    @DeleteMapping("/users/{id}")
    public String delUser(@PathVariable("id") int id){
        if (userService.removeById(id)){
            return Result.ok("删除用户成功");
        } else {
            return Result.error("删除用户失败");
        }
    }
    ```

* 修改

    ```java
    @PutMapping("/users")
    public String updateUser(@RequestBody User user){
        if (userService.updateById(user)){
            return Result.ok("修改用户成功");
        } else {
            return Result.error("修改用户失败");
        }
    }
    ```

## Redis

* Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。 它支持多种类型的数据结构，如 [字符串（strings）](http://www.redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://www.redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://www.redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://www.redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://www.redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://www.redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://www.redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://www.redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://www.redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://www.redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://www.redis.cn/topics/lru-cache.html)，[事务（transactions）](http://www.redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://www.redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://www.redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://www.redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。

#### 起步

* 导入依赖

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    ```

#### 自动配置

* RedisAutoConfiguration 自动配置类。RedisProperties 属性类 --> spring.redis.xxxx 是对 redis 的配置
* 连接工厂是准备好的。LettuceConnectionConfiguration JedisConnnectionConfiguration
* 自动注入了 RedisTemplate<Object, Object>: xxxTemplate
* 自动注入了 StringRedisTemplate; k: v 都是 String
* key: value
* 底层只要我们使用 StringRedisTemplate、RedisTemplate 就可以操作 redis

#### 服务器配置 redis

* 安装 redis

    ```shell
    $ yum install redis
    ```

* 修改 redis 配置，添加远程访问

    ```shell
    # 修改配置文件
    $ vi /etc/redis.conf
    
    # 注释掉 bind 绑定
    # bind 127.0.0.1
    
    # 去除下面注释，并改为自己想要的密码
    requirepass password
    
    # 启动 redis
    $ systemctl start redis.service
    ```

#### 测试 redis

* 添加连接 redis 配置

    ```yaml
    spring:
      redis:
        # 第一种，将所有信息配置到url中，或者分开连接也可，用户名和密码都可省
      	# redis://user:password@example.com:6379
        url: redis://123456@www.xxx.com:6379
        
        # 第二种
        host: example.com
        username: xxx
        password: 123456
        port: 6379
    ```

* 存储和获取

    ```java
    // 注入 String redis 模板
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Test
    void contextLoads() {
        // 创建操作对象
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
    	// 存储数据
        operations.set("hello", "world");
    	
        // 获取数据
        String hello = operations.get("hello");
        System.out.println(hello);
    }
    ```

#### 配置 jedis

* 导入坐标

    ```xml
    <!--  springboot 已经提供了版本仲裁，不需要加入版本号  -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
    </dependency>
    ```

* 添加配置，使用 jedis

    ```yml
    spring:
      redis:
        # 使用 jedis 客户端
        client-type: jedis
    ```

# 5.指标监控

#### 使用方法

* 引入场景启动器

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```

* 测试
    * 访问 `localhost:8080/actuator` 可以查看监控信息
    * 访问 `localhostL8080/actutor/health` 可以查看当前应用的运行状态，UP/DOWN

* Endpoint 查询可以参考文档

    [Spring Boot Actuator Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints)

    [Spring Boot Actuator Web API Documentation](https://docs.spring.io/spring-boot/docs/2.5.0/actuator-api/htmlsingle/)

#### 暴露 Endpionts

* 默认只暴露了 health

* 配置暴露所有 endpoints

    ```yaml
    # managment 是所有 actutor 的配置 
    management:
      endpoints:
        enabled-by-default: true  # 默认开启所有监控端点
      	web:
          exposure:
            include: '*' # 以 web 方式暴露所有端点
      endpoint:
      # 某个端点的具体配置
        health:
          show-details: always # 显示健康的详情
    ```

#### Health Endpoints

* 健康检查端点，我们一般用于在云平台，平台会定时的检查应用的健康状况，我们就需要Health Endpoint可以为平台返回当前应用的一系列组件健康状况的集合。

* 重要的几点：
    * health endpoint返回的结果，应该是一系列健康检查后的一个汇总报告
    * 很多的健康检查默认已经自动配置好了，比如：数据库、redis等
    * 可以很容易的添加自定义的健康检查机制

#### Metrice Endpoint

* 提供详细的、层级的、空间指标信息，这些信息可以被pull（主动推送）或者push（被动获取）方式得到；
    * 通过Metrics对接多种监控系统
    * 简化核心Metrics开发
    * 添加自定义Metrics或者扩展已有Metrics
