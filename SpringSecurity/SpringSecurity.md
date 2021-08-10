## Spring Security 简介

#### 概要

* Spring 是非常流行和成功的 Java 应用开发框架，Spring Security正是Spring家族中的成员。Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方案。
* 正如你可能知道的关于安全方面的两个主要区域是“认证”和“授权”（或者访问控制），一般来说，Web 应用的安全性包括用户认证（Authentication）和用户授权（Authorization）两个部分，这两点也是Spring Security重要核心功能。
    * （Authentication）用户认证指的是：验证某个用户是否为系统中的合法主体，也就是说用户能否访问该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认证过程。通俗点说就是系统认为用户是否能登录
    * （Authorization）用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以进行修改。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的权限。通俗点讲就是系统判断用户是否有权限去做某些事情。

#### 比较

* Spring Security
    * [Spring Security 官网](https://spring.io/projects/spring-security)
    * Spring Security 特点
        * 和 Spring 无缝整合。
        * 全面的权限控制。
        * 专门为 Web 开发而设计，新版本对整个框架进行了分层抽取，分成了核心模块和 Web 模块。单独引入核心模块就可以脱离Web 环境。
        * 重量级
* Shiro
    * [Shiro 官网](http://shiro.apache.org/)
    * 轻量级。Shiro 主张的理念是把复杂的事情变简单。针对对性能有更高要求的互联网应用有更好表现。
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
    * 将操作系统的“权力”，“授予”，“主体”，这样主体就具备了操作系统中特定功能的能力。
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

* Spring Security 本质是一个过滤器链
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
        
        @GetMapping("/annotation")
        @Secured({"ROLE_user", "ROLE_admin1"}) // 指定 user 和 admin 两个角色可以访问此接口
        public String annotation(){
            return "Hello Annotation";
        }
        
    }
    ```

* 可以在 `userServiceDetails` 中设置角色

#### @PreAuthorize，@PostAuthorize

* `@PreAuthorize` 适合进入方法前的权限验证，可以将登录用户的 roles/permissions 参数传入到方法中

    ```java
    // SpringSecurityDemo02.com.joush.controller.TestController.java
    @RestController
    public class TestController {
        
        @GetMapping("/index")
        @PreAuthorize("hasAnyAuthority('admin')") // 指定 user 和 admin 两个角色可以访问此接口
        public String index(){
            return "Hello Index";
        }	
        
    }
    ```

* 需要在主启动类开启这两个注解的使用

    ```java
    // SpringSecurityDemo02.com.joush.controller.TestController.java
    @SpringBootApplication
    // 设置prePostEnables = true 即可
    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
    public class SpringSecurityDemo02Application {
    
        public static void main(String[] args) {
            SpringApplication.run(SpringSecurityDemo02Application.class, args);
        }
    
    }
    ```

* `@PostAuthorize` 注解使用的并不多，在执行方法后在进行权限验证，适合验证带有返回值的权限

    ```java
    // SpringSecurityDemo02.com.joush.controller.TestController.java
    @GetMapping("/prePost")
    @PostAuthorize("hasAnyAuthority('admin')")
    public String prePostEnabled(){
        System.out.println("aaa");	// 页面会提示没有权限，但此方法以及执行完毕了，控制台打印输出了
        return "Hello prePost";
    }
    ```

#### @PostFilter，@PreFilter

* `@PostFilter` 权限验证之后对返回数据进行过滤，留下用户名是 admin1 的数据

* `@PreFilter` 对传入的数据进行过滤，只能传入 admin1 的数据

    * 表达式中的 filterObject 引用的是方法返回值 List 中的某一个元素

        ```java
        // SpringSecurityDemo02.com.joush.controller.TestController.java
        @GetMapping("/prePostFilter")
        @PostAuthorize("hasAnyRole('admin')")
        @PostFilter("filterObject.username == '李一航'")	// 设置只有用户名为 李一航 的 User 才能返回
        public List<User> prePostFilter() {
        
            List<User> users = new ArrayList<>();
            users.add(new User(12, "李一航", "123456", 12, "aaa"));
            users.add(new User(13, "陈一然", "123456", 12, "aaa"));
            return users;
        }
        ```

    * 测试，访问 <http://localhost:8080/prePostFilter> 发现返回结果只有第一条，而不是两条都有

#### 注销功能

* 添加登出的配置

    ```java
    // SpringSecurityDemo02.com.joush.config.SecurityConfig.java
    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
    	@Override
    	protected void configure(HttpSecurity http) throws Exception {
    
        	// 注销
        	http.logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
    	}
    }
    ```

* 测试

    * 在成功页面添加注销按钮

        ```html
        <!-- SpringSecurityDemo02.resources.static.home.html -->
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>Title</title>
        </head>
        <body>
        <h1>登陆成功</h1><br>
        <button>
            <a href="/logout">登出</a>
        </button>
        </body>
        </html>
        ```

    * 登录成功后，在成功页面再去访问其他 controller 则不允许

## CSRF

#### CSRF 理解

* 跨站请求伪造（英语：Cross-Site Request Forgery），也被称为 one-click attack 或者 session riding，通常缩写为 CSRF 或者 XSRF，
* CSRF 是一种挟制用户在当前已登录的 Web 应用程序上执行非本意的操作的攻击方法。跟跨网站脚本（XSS）相比，XSS 利用的是用户对指定网站的信任，CSRF 利用的是网站对用户网页浏览器的信任。

* 跨站请求攻击，简单地说，是攻击者通过一些技术手段欺骗用户的浏览器去访问一个自己曾经认证过的网站并运行一些操作（如发邮件，发消息，甚至财产操作如转账和购买商品）。由于浏览器曾经认证过，所以被访问的网站会认为是真正的用户操作而去运行。这利用了 web 中用户身份验证的一个漏洞：简单的身份验证只能保证请求发自某个用户的浏览器，却不能保证请求本身是用户自愿发出的。

* 通俗理解，在 A 网站进行登陆了后，浏览器不关闭，同时打开 B 网站，在 B 网站即可访问 A 网站中的一些内容，而绕开验证部分。

* 从 Spring Security 4.0 开始，默认情况下会启用 CSRF 保护，以防止 CSRF 攻击应用程序，Spring Security CSRF 会针对 PATCH，POST，PUT 和 DELETE 方法进行防护。
