package com.joush;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootTest
class Boot04DatasourceApplicationTests {

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    DataSource dataSource;

    @Test
    void contextLoads() {

//        jdbcTemplate.queryForObject("select * from user where id = 2");
//        jdbcTemplate.queryForList("select * from user");
        Integer integer = jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
        System.out.println(integer);

        System.out.println(dataSource.getClass());
    }

}
