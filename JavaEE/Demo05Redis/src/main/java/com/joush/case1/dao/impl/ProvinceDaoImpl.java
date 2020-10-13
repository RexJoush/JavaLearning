package com.joush.case1.dao.impl;

import com.joush.case1.dao.ProvinceDao;
import com.joush.case1.domain.Province;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProvinceDaoImpl implements ProvinceDao {

    // 声明成员变量 jdbcTemplement
    private JdbcTemplate template = new JdbcTemplate();

    @Override
    public List<Province> findAll() {
        return null;
    }
}
