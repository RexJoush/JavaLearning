package com.my.day19.demo02JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
    添加一条记录
 */
public class Demo02Insert {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;


        try {
            // 1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获取连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","liyihang123");
            // 3.定义 sql
            String sql = "insert into temp_humi values('2','25','25')";
            // 4.获取执行的 sql 对象
            statement = connection.createStatement();

            // 5.获取结果
            int i = statement.executeUpdate(sql);

            // 6.处理结果
            System.out.println(i);
            if (i > 0){
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

            // 避免空指针异常
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
    }
}
