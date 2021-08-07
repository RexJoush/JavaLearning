//package com.joush.config;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * @author Rex Joush
// * @time 2021.03.23
// */
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    // 添加认证
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        // 首页所有人可以访问，功能页只有对应有权限的人才能访问
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/level1/**").hasRole("vip1")
//                .antMatchers("/level2/**").hasRole("vip2")
//                .antMatchers("/level3/**").hasRole("vip3");
//
//        // 没有权限默认到 login 页面
//        http.formLogin().loginPage("/toLogin");
//
//        // 开启注销功能，并且注销成功后前往主页
//        http.logout().logoutSuccessUrl("/");
//    }
//
//
//    // 添加授权
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        // 授权用户具有那些权限
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("joush").password(new BCryptPasswordEncoder().encode("111111")).roles("vip2", "vip3")
//                .and()
//                .withUser("root").password(new BCryptPasswordEncoder().encode("111111")).roles("vip1", "vip2", "vip3")
//                .and()
//                .withUser("guest").password(new BCryptPasswordEncoder().encode("111111")).roles("vip1");
//    }
//}
