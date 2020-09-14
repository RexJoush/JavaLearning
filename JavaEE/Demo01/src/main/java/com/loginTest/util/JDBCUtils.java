package com.loginTest.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *  JDBC 工具类，使用 Durid 连接池
 */
public class JDBCUtils {

    private static DataSource ds;

    // 初始化连接池
    static {
        try {
            // 1.加载配置文件
            Properties properties = new Properties();

            // 2.使用 ClassLoader 加载配置文件
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(is);

            // 3.初始化连接池对象
            ds = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接池对象
     * @return 连接池对象
     */
    public static DataSource getDataSource(){
        return ds;
    }

    /**
     * 获取连接 Connection 对象
     * @return 连接对象
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
