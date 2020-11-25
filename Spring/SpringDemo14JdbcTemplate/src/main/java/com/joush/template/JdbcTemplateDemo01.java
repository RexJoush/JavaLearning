package com.joush.template;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * JdbcTemplate 的基本用法
 */
public class JdbcTemplateDemo01 {

    public static void main(String[] args) {
        // 0.配置数据源
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/joush?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("liyihang123");

        // 1.创建 JdbcTemplate 对象
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource);

        // 2.执行操作
        jdbcTemplate.execute("insert into new_account (name, money) values ('ddd', 1000)");

    }

}
