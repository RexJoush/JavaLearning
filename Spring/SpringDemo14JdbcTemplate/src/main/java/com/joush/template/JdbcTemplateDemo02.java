package com.joush.template;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcTemplate 的基于 Ioc 的用法
 */
public class JdbcTemplateDemo02 {

    public static void main(String[] args) {

        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.获取对象
        JdbcTemplate template = ac.getBean("jdbcTemplate", JdbcTemplate.class);

        // 3.执行操作
        template.execute("insert into new_account (name, money) values ('eee', 2000)");

    }

}
