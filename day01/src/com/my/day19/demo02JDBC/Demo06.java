package com.my.day19.demo02JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
    练习，定义一个方法，查询表并将其结果封装成对象
 */
public class Demo06 {


    public static void main(String[] args) {
        System.out.println(new Demo06().findAll());
    }


    /**
     * 获取查询结果
     * @return 结果列表
     */
    public List<Meeting> findAll(){
        // 设置集合
        List<Meeting> list = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获取连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","liyihang123");
            // 3.定义 sql
            String sql = "select * from meeting";
            // 4.获取执行的 sql 对象
            statement = connection.createStatement();

            // 5.获取结果
            resultSet = statement.executeQuery(sql);

            // 6.处理结果
            // 7.获取数据
            Meeting meeting = null;
            while (resultSet.next()){
                meeting = new Meeting(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5)
                ,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10));
                list.add(meeting);
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

            // 避免空指针异常
            if (resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
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
        return list;
    }

    /**
     * 获取查询结果，使用自定义 Utils 工具类实现
     * @return 结果列表
     */
    public List<Meeting> findAll2(){
        // 设置集合
        List<Meeting> list = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动
            //Class.forName("com.mysql.cj.jdbc.Driver");
            // 2.获取连接
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/graduation?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","liyihang123");

            conn = JDBCUtils.getConnection();

            // 3.定义 sql
            String sql = "select * from meeting";
            // 4.获取执行的 sql 对象
            statement = conn.createStatement();

            // 5.获取结果
            resultSet = statement.executeQuery(sql);

            // 6.处理结果
            // 7.获取数据
            Meeting meeting = null;
            while (resultSet.next()){
                meeting = new Meeting(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5)
                        ,resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getString(10));
                list.add(meeting);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(resultSet,statement,conn);
        }
        return list;
    }
}

