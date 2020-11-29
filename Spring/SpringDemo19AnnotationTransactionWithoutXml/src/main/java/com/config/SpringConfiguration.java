package com.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring 的配置类，相当于 bean.xml
 */
@Configuration
@ComponentScan("com.joush")
@Import({JdbcConfig.class,TransactionConfig.class})
@PropertySource("jdbcConfig.properties")
@EnableTransactionManagement // 开启事务注解支持
public class SpringConfiguration {

}
