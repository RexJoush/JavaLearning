package com.joush.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Rex Joush
 * @time 2021.05.18 20:12
 */

@Configuration
public class MyDataSourceConfig {


    // 默认的自动配置是判断容器中没有数据源才会配 @ConditionalOnMissingBean(DataSource.class)
    @ConfigurationProperties("spring.datasource")
    @Bean // 注入数据源
    public DataSource dataSource(){

        DruidDataSource dataSource = new DruidDataSource();

        // 不需要设置，使用 Properties 配置即可
//        dataSource.setUrl();
        return dataSource;
    }

    /**
     * 配置 druid 的监控页功能，学习注入 Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        StatViewServlet statViewServlet = new StatViewServlet();

        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");

        return registrationBean;
    }

    /**
     * 学习注入 Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){

        WebStatFilter webStatFilter = new WebStatFilter();

        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean(webStatFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*")); // 所以请求都拦截

        // 配置静态资源不拦截
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.css，*.gif,*.jpg,*.png,*.ico,/druid/*");

        return filterRegistrationBean;

    }

}
