package com.joush.day19.demo02JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
    更新记录
 */
public class Demo03Update {
    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","liyihang123");

            String sql = "update temp_humi set temperature = 28 where id = 1";
            statement = connection.createStatement();
            int i = statement.executeUpdate(sql);

            System.out.println(i);
            if (i > 0){
                System.out.println("更新成功");
            } else {
                System.out.println("更新失败");
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
