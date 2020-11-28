package com.joush.utils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接的工具类，用于从数据源中获取连接，并且实现和线程的绑定
 */
public class ConnectionUtils {

    private ThreadLocal<Connection> threadLocal = new InheritableThreadLocal<>();


    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection(){
        // 1.先从 Thread 上获取
        Connection connection = threadLocal.get();

        try {
            // 2.判断当前线程上是否有连接
            if (connection == null) {
                // 3.从数据源中获取一个连接，并且存入 ThreadLocal 中
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }

            // 4.返回当前线程上的连接
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    /**
     * 将线程和连接解绑
     */
    public void removeConnection(){
        threadLocal.remove();
    }

}
