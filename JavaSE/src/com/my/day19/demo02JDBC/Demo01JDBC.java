package com.my.day19.demo02JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
    JDBC 快速入门
 */
public class Demo01JDBC {

    public static void main(String[] args) throws Exception {
        // 1.导入jar包
        // 2.注册驱动, 在mysql 5之后可以不写注册驱动
        // Class.forName("com.mysql.cj.jdbc.Driver");

        // 3.获取数据库连接对象
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","liyihang123");

        // 4.定义 sql 语句
        String sql = "select * from meeting";

        // 5.获取执行 sql 的对象 Statement
        Statement statement = connection.createStatement();

        // 6.执行 sql
        ResultSet resultSet = statement.executeQuery(sql);

        // 处理结果
        System.out.println(resultSet.toString());
        // 7.释放资源
        statement.close();
        connection.close();

    }

}
