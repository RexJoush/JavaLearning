package com.joush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)  // 开启注解 spring security 的支持
public class SpringSecurityDemo02Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemo02Application.class, args);
    }

}
