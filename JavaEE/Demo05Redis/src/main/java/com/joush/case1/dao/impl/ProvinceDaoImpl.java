package com.joush.case1.dao.impl;

import com.joush.case1.dao.ProvinceDao;
import com.joush.case1.domain.Province;
import com.joush.case1.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProvinceDaoImpl implements ProvinceDao {

    // 1.声明成员变量 jdbcTemplate
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Province> findAll() {


        // 定义sql
        String sql = "select * from province";

        List<Province> query = template.query(sql, new BeanPropertyRowMapper<Province>(Province.class));

        return query;

    }
}
