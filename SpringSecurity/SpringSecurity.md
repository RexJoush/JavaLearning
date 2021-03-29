## introduction

* Spring Security 是针对于 Spring Boot 的安全框架。也是 Spring Boot 底层安全模块默认的技术选型，可以实现强大的 Web 安全控制。 
* 对于安全控制，仅需引入 `spring-boot-starter-security` 模块，进行少量配置即可

* 一些主要的类
    - WebSecurityConfigurerAdapter 
    - AuthenticationManagerBuilder
    - @EnableWebSecurity

* Spring Security 的两个主要目标是 Authentication(认证) 和 Authorization(授权)
