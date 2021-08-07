## Introduction

* Spring Security 是针对于 Spring Boot 的安全框架。也是 Spring Boot 底层安全模块默认的技术选型，可以实现强大的 Web 安全控制。 
* 对于安全控制，仅需引入 `spring-boot-starter-security` 模块，进行少量配置即可

* 一些主要的类
    - WebSecurityConfigurerAdapter，自定义 Security 策略
    - AuthenticationManagerBuilder，自定义认证策略
    - @EnableWebSecurity，开启 WebSecurity 模式

* Spring Security 的两个主要目标是 Authentication(认证) 和 Authorization(授权)

## 使用

#### 准备静态资源

* 引入 thymeleaf

* 建立三个等级的静态页面 level1，level2，level3

    ```text
    SpringSecurityDemo01 resource 目录下的文件结构
    resource|
    		|static|
    		|	   |qinjiang
    		|templates|
    				  |views|
    				  		|level1|
    				  		|	   |1.html
    				  		|	   |2.html
    				  		|	   |3.html
    				  		|level2
    				  		|
    				  		|levie3
    						|
    ```

#### 认证

* 新建配置类，重写方法

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityConfig.java
    import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
    // 添加注解，启用 spring security
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
    
            // 首页所有人可以访问，功能页只有对应有权限的人才能访问
            http.authorizeRequests()
                    .antMatchers("/").permitAll()				// 首页放行所有
                    .antMatchers("/level1/**").hasRole("vip1")	// level1 vip1才能访问
                    .antMatchers("/level2/**").hasRole("vip2")	// level2 vip2才能访问
                    .antMatchers("/level3/**").hasRole("vip3");	// level3 vip3才能访问
    
        }
        
        // 没有权限默认到 login 页面
        http.formLogin();
        
    }
    ```

* 此时再访问 level1-1 或者 level2-3 都将提示跳转登陆页面

#### 授权

* 重写 `WebSecurityConfigurerAdapter` 的重载方法

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityConfig.java
    // 添加授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
        // 授权用户具有那些权限
        auth.inMemoryAuthentication()
            .withUser("joush").password("111111").roles("vip2", "vip3")
            .and()
            .withUser("root").password("111111").roles("vip1", "vip2", "vip3")
            .and()
            .withUser("guest").password("111111").roles("vip1");
    }
    ```

* 在 spring boot 2.0 以后，需要添加密码验证后才能实现

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityConfig.java
    // 添加授权
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
        // 授权用户具有那些权限
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser("joush").password(new BCryptPasswordEncoder().encode("111111")).roles("vip2", "vip3")
                    .and()
                    .withUser("root").password(new BCryptPasswordEncoder().encode("111111")).roles("vip1", "vip2", "vip3")
                    .and()
                    .withUser("guest").password(new BCryptPasswordEncoder().encode("111111")).roles("vip1");
        
    }
    ```

* 注销

    * 页面添加注销按钮

        ```html
        <!-- SpringSecurityDemo01.resources.templates.index.html -->
        <!-- 注销 -->
        <a class="item" th:href="@{/logout}">
            <i class="sign-out icon"></i> 注销
        </a>
        ```

    * 配置项添加注销功能

        ```java
        // SpringSecurityDemo01.com.joush.config.SecurityConfig.java
        // 开启注销功能，并且注销成功后前往主页
        http.logout().logoutSuccessUrl("/");
        ```

        

## SpringSecurity 简介

#### 概要

* Spring 是非常流行和成功的 Java 应用开发框架，Spring Security正是Spring家族中的成员。Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方案。
* 正如你可能知道的关于安全方面的两个主要区域是“认证”和“授权”（或者访问控制），一般来说，Web 应用的安全性包括用户认证（Authentication）和用户授权（Authorization）两个部分，这两点也是Spring Security重要核心功能。
    * （Authentication）用户认证指的是：验证某个用户是否为系统中的合法主体，也就是说用户能否访问该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认证过程。通俗点说就是系统认为用户是否能登录
    * （Authorization）用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以进行修改。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的权限。通俗点讲就是系统判断用户是否有权限去做某些事情。

#### 比较

* Spring Security
    * [Spring Security 官网](https://spring.io/projects/spring-security)
    * Spring Security特点
        * 和Spring无缝整合。
        * 全面的权限控制。
        * 专门为Web开发而设计，新版本对整个框架进行了分层抽取，分成了核心模块和Web模块。单独引入核心模块就可以脱离Web环境。
        * 重量级
* Shiro
    * [Shiro官网](http://shiro.apache.org/)
    * 轻量级。Shiro主张的理念是把复杂的事情变简单。针对对性能有更高要求的互联网应用有更好表现。
    * 通用性。

#### 模块划分

* Core — spring-security-core.jar
* Remoting — spring-security-remoting.jar
* Web — spring-security-web.jar
* Config— spring-security-config.jar
* LDAP — spring-security-ldap.jar
* OAuth 2.0 Core — spring-security-oauth2-core.jar
* OAuth 2.0 Client — spring-security-oauth2-client.jar
* OAuth 2.0 JOSE — spring-security-oauth2-jose.jar
* OAuth 2.0 Resource Server — spring-security-oauth2-resource-server.jar
* ACL — spring-security-acl.jar
* CAS — spring-security-cas.jar
* OpenID — spring-security-openid.jar
* Test — spring-security-test.jar

#### 权限管理中的相关概念

* 主体（principal）
    * 使用系统的用户或设备或从其他系统远程登录的用户等等。简单说就是谁使用系统谁就是主体。
* 认证（authentication）
    * 权限管理系统确认一个主体的身份，允许主体进入系统。简单说就是“主体”证明自己是谁。
    * 笼统的认为就是以前所做的登录操作。
* 授权（authorization）
    * 将操作系统的“权力”“授予”“主体”，这样主体就具备了操作系统中特定功能的能力。
    * 简单来说，授权就是给用户分配权限。

## Spring Security 入门案例

#### 创建 Spring Boot 项目

* 添加依赖

    ```xml
    <!-- SpringSecurityDemo01.pom.xml  -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    ```

* 添加 controller 测试

    ```java
    // SpringSecurityDemo01.com.joush.controller.TestController.java
    @RestController
    public class TestController {
    
        @GetMapping("/hello")
        public String hello(){
            return "Hello Security";
        }
    
    }
    ```

* 访问 <http://localhost:8080/hello>，发现自动跳转登陆页面，此页面为 spring security 默认登录页，用户名是 user，密码是控制台打印的字符串

    ```shell
    # 控制台信息，后面的字符串就是密码
    2021-08-05 15:15:23.039  INFO 3092 --- [           main] .s.s.UserDetailsServiceAutoConfiguration : 
    
    Using generated security password: 209473bb-71fa-4d74-ba8d-d85eb2cce194
    
    2021-08-05 15:15:23.102  INFO 3092 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Will sec
    ```

#### Spring Security 基本原理

* SpringSecurity 本质是一个过滤器链
* 部分过滤器
    * FilterSecurityInterceptor，一个方法级的权限过滤器，基本位于过滤器的最底部
    * ExceptionTranslationFilter，异常过滤器，用来处理在认证授权过程中抛出的异常
    * UsernamePasswordAuthentivationFilter，对 /login 的 POST 请求做拦截，校验表单的用户名和密码
* UserDetailService 接口
    * 当什么也没有配置的时候，账号和密码是由Spring Security定义生成的。而在实际项目中账号和密码都是从数据库中查询出来的。 所以我们要通过自定义逻辑控制认证逻辑。
    * 此接口用来实现自定义验证用户名和密码
    * 使用方式
        * 创建类继承 UsernamePasswordAuthentivationFilter，重写三个方法
        * 创建类实现 UserDetailService，编写查询数据过程，返回 User 对象，此 User 对象是安全框架提供的对象
* PasswordEncoder 接口
    * 数据加密接口，用于对返回 User 对象里面的密码进行加密

## Spring Security Web 认证

#### 设置登录的用户名密码

* 配置文件方式

    * 在 application.yml 里配置即可

        ```yml
        # SpringSecurityDemo01.src.resources.application.yml
        spring:
          security:
            user:
              name: joush
              password: 111111
        ```

* 配置类

    * 新建配置类

        ```java
        // SpringSecurityDemo01.com.joush.config.SecurityConfig.java
        @Configuration
        public class SecurityConfig extends WebSecurityConfigurerAdapter {
        
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
                // 加密对象
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
                // 添加用户授权
        		auth.inMemoryAuthentication().withUser("bonny").password(passwordEncoder.encode("123")).roles("admin");
            }
            
            // 添加加密类到容器中
            @Bean
            public PasswordEncoder password(){
                return new BCryptPasswordEncoder();
            }
        }
        ```

* 自定义编写实现类

    * 创建配置类，设置使用哪个 userDetailsService 实现类

        ```java
        // SpringSecurityDemo01.com.joush.config.SecurityDataBaseConfig.java
        @Configuration
        public class SecurityDataBaseConfig extends WebSecurityConfigurerAdapter {
        
            @Resource
            private UserDetailsService userDetailsService;
        
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                // 使用 UserDetailsService，密码进行加密
                auth.userDetailsService(userDetailsService).passwordEncoder(password());
            }
        
            @Bean
            public PasswordEncoder password(){
                return new BCryptPasswordEncoder();
            }
        }
        ```

    * 编写实现类，返回 User 对象，User 对象有用户名密码和操作权限，此时用户名和密码不是从数据库中查到的

        ```java
        // SpringSecurityDemo01.com.joush.service.MyUserDetailsService.java
        @Service // 实现 UserDetailsService 接口的方法
        public class MyUserDetailsService implements UserDetailsService {
        
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        
                // 权限列表
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
                // 返回当前用户的用户名，密码，以及权限列表
                return new User("marry", new BCryptPasswordEncoder().encode("123"), authorities);
            }
        }
        ```

    * 查询数据库完成校验，此时需要数据库的集成，这里使用了 mybatis-plus

        ```java
        // SpringSecurityDemo01.com.joush.service.MyUserDetailsService.java
        @Service
        public class MyUserDetailsService implements UserDetailsService {
        
            @Resource
            private UserMapper userMapper;
        
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        		// 使用 mybatis-plus 查询用户
                QueryWrapper<com.joush.entities.User> wrapper = new QueryWrapper<>();
                //wrapper.eq("username", s);
                // 查询结果
                com.joush.entities.User user = userMapper.selectOne(wrapper.lambda().eq(com.joush.entities.User::getUsername, s));
        
                // 说明数据库中没有用户名
                if (user == null) {
                    throw new UsernameNotFoundException("用户名不存在!");
                }
        	
                // 查到结果后返回 User 对象
                // 权限列表
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
                // 返回当前用户的用户名，密码，以及权限列表
                return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
            }
        }
        ```

#### 自定义登录页面

* 新建登录页

    ```html
    <!-- SpringSecurityDemo01.src.resources.static.login.html -->
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Document</title>
    </head>
    <body>
    <!-- 此处表单的用户名和密码，name 属性必须写 username 和 password，否则 spring security 无法进行校验 -->
    <form action="/user/login" method="post">
        username: <input type="text" name="username">
        <br>
        password: <input type="password" name="password">
        <input type="submit" value="login">
    </form>
    </body>
    </html>
    ```

* 添加配置

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityDataBaseConfig.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 自定义表单登录页面
            .loginPage("/login.html")                           // 登陆页面设置
            .loginProcessingUrl("/user/login")                  // 登陆成功后交到哪个 controller 中
            .defaultSuccessUrl("/index").permitAll()        // 登陆成功后跳转的路径
            .and().authorizeRequests()
            .antMatchers("/", "/hello", "/user/login").permitAll() // 设置那些路径可以直接访问，不需要认证
            .anyRequest().authenticated()
            .and().csrf().disable();                            // 关闭 csrf 的防护
    }
    ```

* 添加 Controller 部分

    ```java
    // SpringSecurityDemo01.com.joush.controller.TestController.java
    @RestController
    public class TestController {
    
        @GetMapping("/hello")
        public String hello(){
            return "Hello Security";
        }
    
    
        @GetMapping("/index")
        public String index(){
            return "Hello Index";
        }
    
    }
    ```

* 测试发现，<http://localhost:8080/hello> 可以直接访问，不需要验证
* <http://localhost:8080/index> 则自动进入登录页面，此页面为自定义页面

## Spring Security Web 授权

#### 授权访问控制

* 如果当前主体具有指定的权限，返回 true，否则返回 false

* 在配置类当中，设置当前访问地址有那些权限

* 验证权限

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityDataBaseConfig.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 自定义表单登录页面
            .loginPage("/login.html")                           // 登陆页面设置
            .loginProcessingUrl("/user/login")                  // 登陆成功后交到哪个 controller 中
            .defaultSuccessUrl("/index").permitAll()        // 登陆成功后跳转的路径
            .and().authorizeRequests()
            .antMatchers("/", "/hello", "/user/login").permitAll() // 设置那些路径可以直接访问，不需要认证
            
            // 当前登录用户，只有拥有 admin 权限才能访问
            // .antMatchers("/index").hasAuthority("admin")
            // 当前登录用户，有 admin 或者 manage 权限都可以
            .antMatchers("/index").hasAnyAuthority("admin", "manage")
            
            .anyRequest().authenticated()
            .and().csrf().disable();                            // 关闭 csrf 的防护
    }
    ```

* 授权

    ```java
    // SpringSecurityDemo01.com.joush.service.MyUserDetailsService.java
    @Service
    public class MyUserDetailsService implements UserDetailsService {
    
        @Resource
        private UserMapper userMapper;
    
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    
            // ···
    
            // 权限列表，此处给予的权限在配置部分进行配置即可，给单独权限
            List<GrantedAuthority> authorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
            
            // 给多个权限
            List<GrantedAuthority> authorities =
                    AuthorityUtils.createAuthorityList("manage", "admin");
            
            // ···
            
            // 返回当前用户的用户名，密码，以及权限列表
            return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
        }
    }
    ```

#### 角色访问控制

* 需要注意，在添加角色的时候，需要添加 `ROLE_` 前缀，但在验证的时候是不需要加的

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityDataBaseConfig.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 自定义表单登录页面
            .loginPage("/login.html")                           // 登陆页面设置
            .loginProcessingUrl("/user/login")                  // 登陆成功后交到哪个 controller 中
            .defaultSuccessUrl("/index").permitAll()        	// 登陆成功后跳转的路径
            .and().authorizeRequests()
            .antMatchers("/", "/hello", "/user/login").permitAll() // 设置那些路径可以直接访问，不需要认证
    
            /*
                   基于角色的权限控制，此处在授权的时候需要注意加相关的前缀
                */
            // 单角色控制
            .antMatchers("/index").hasRole("manager")
            // 多角色控制
            .antMatchers("/index").hasAnyRole("manager", "user")
    
            .anyRequest().authenticated()
            .and().csrf().disable();                            // 关闭 csrf 的防护
    }
    ```

* 授权的特殊点

    ```java
    // SpringSecurityDemo01.com.joush.service.MyUserDetailsService.java
    @Service
    public class MyUserDetailsService implements UserDetailsService {
    
        @Resource
        private UserMapper userMapper;
    
        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    
            // ···
    
            // 角色权限列表，给予 admin 角色，此处在配置验证部分，不需要加前缀
            List<GrantedAuthority> authorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
            
            // 此处可以给予权限，同时也可给予角色，下面表示给与 manage 权限和 admin 角色
            List<GrantedAuthority> authorities =
                    AuthorityUtils.createAuthorityList("manage", "ROLE_admin");
            
            // ···
            
            // 返回当前用户的用户名，密码，以及权限列表
            return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), authorities);
        }
    }
    ```

#### 自定义 403 禁止页面

* 修改访问配置类

    ```java
    // SpringSecurityDemo01.com.joush.config.SecurityDataBaseConfig.java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        // 配置没有访问权限跳转的自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        
        http.formLogin() 	// 自定义表单登录页面
           	// ···
            .and().csrf().disable();       // 关闭 csrf 的防护
    }
    ```

* 添加页面

    ```html
    <!-- SpringSecurityDemo01.src.resources.static.unauth.html -->
    <!doctype html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Document</title>
    </head>
    <body>
        <h1>没有访问权限</h1>
    </body>
    </html>
    ```

## 认证授权的注解使用

#### @Secured

* 启动类开启注解支持

    ```java
    // SpringSecurityDemo02.com.joush.SpringSecurityDemo02Application.java
    @SpringBootApplication
    @EnableGlobalMethodSecurity(securedEnabled = true)  // 开启注解 spring security 的支持
    public class SpringSecurityDemo02Application {
    
        public static void main(String[] args) {
            SpringApplication.run(SpringSecurityDemo02Application.class, args);
        }
    
    }
    ```

* 在 controller 中添加 `@Secured`注解，设置访问角色

    ```java
    // SpringSecurityDemo02.com.joush.controller.TestController.java
    @RestController
    public class TestController {
        
        @GetMapping("/index")
        @Secured({"ROLE_user", "ROLE_admin"}) // 指定 user 和 admin 两个角色可以访问此接口
        public String index(){
            return "Hello Index";
        }
        
    }
    ```

* 在 `userServiceDetails` 中设置角色

#### @PreAuth

* `@PreAuth` 适合进入方法前的权限验证
