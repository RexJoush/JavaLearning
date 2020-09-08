package com.my.day19.demo02JDBC;

import java.sql.*;

/*

 */
public class Demo05Query {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获取连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","liyihang123");
            // 3.定义 sql
            String sql = "select * from temp_humi";
            // 4.获取执行的 sql 对象
            statement = connection.createStatement();

            // 5.获取结果
            resultSet = statement.executeQuery(sql);

            // 6.处理结果
            // 7.获取数据
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String temp = resultSet.getString("temperature");
                String humi = resultSet.getString(3);

                System.out.println(id + "---" + temp + "--" + humi);
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
