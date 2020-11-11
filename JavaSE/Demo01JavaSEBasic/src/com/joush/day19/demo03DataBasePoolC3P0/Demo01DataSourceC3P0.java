package com.joush.day19.demo03DataBasePoolC3P0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Demo01DataSourceC3P0 {
    public static void main(String[] args) throws SQLException {
        // 1.创建数据库连接池对象，空参使用默认配置
        DataSource ds = new ComboPooledDataSource();

        // 2.创建数据库对象，使用命名配置，参数为配置名
        DataSource ds2 = new ComboPooledDataSource("otherc3p0");

        // 2.获取连接池对象
        Connection connection = ds.getConnection();
        System.out.println(connection);

    }
}
