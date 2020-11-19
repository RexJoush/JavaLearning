package com.joush.mybatis.utils;

import com.joush.mybatis.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 用于创建数据源的工具类
 * 获取数据库连接对象
 */
public class DataSourceUtil {

    /**
     * 获取一个数据源
     * @param configuration
     * @return
     */
    public static Connection getConnection(Configuration configuration){
        try {
            Class.forName(configuration.getDriver());
            return DriverManager.getConnection(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
