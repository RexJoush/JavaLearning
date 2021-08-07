package com.joush.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author Rex Joush
 * @time 2021.08.05 20:51
 */
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 配置没有访问权限跳转的自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin() // 自定义表单登录页面
                .loginPage("/login.html")                           // 登陆页面设置
                .loginProcessingUrl("/user/login")                  // 登陆成功后交到哪个 controller 中
                .defaultSuccessUrl("/index").permitAll()        // 登陆成功后跳转的路径
                .and().authorizeRequests()
                .antMatchers("/", "/hello", "/user/login").permitAll() // 设置那些路径可以直接访问，不需要认证

                /*
                    基于授权的权限控制
                 */

                // 当前登录用户，只有拥有 admin 权限才能访问
                // .antMatchers("/index").hasAuthority("admin")

                // 当前登录用户，有 admin 或者 manage 权限都可以
                // .antMatchers("/index").hasAnyAuthority("admin", "manage")


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
}
