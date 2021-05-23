package com.joush.config;

import com.joush.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Rex Joush
 * @time 2021.05.10 10:35
 */

/**
 * 需要实现 WebMvcConfigurer 接口 配置 mvc 的配置项
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 添加拦截器
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")     // 拦截所有 // 此处静态资源也会被拦截
//                .excludePathPatterns("/login","/", "/css/**"); // 放行登录, 并且放行静态资源
    }
}
