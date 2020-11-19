package com.joush.day19.demo02JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Demo07Transaction {
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        try {
            // 获取连接对象
            connection = JDBCUtils.getConnection();

            // 开启事务
            connection.setAutoCommit(false);

            String sql1 = "update account set balance = balance - ? where id = ?";
            String sql2 = "update account set balance = balance + ? where id = ?";

            // 获取执行 sql 对象
            pst1 = connection.prepareStatement(sql1);
            pst2 = connection.prepareStatement(sql2);

            // 设置参数
            pst1.setInt(1,500);
            pst1.setInt(2,1);
            pst2.setInt(1,500);
            pst2.setInt(2,2);

            // 执行 sql
            pst1.executeUpdate();
            pst1.executeUpdate();

            // 提交事务
            connection.commit();
        } catch (Exception e) {
            try {
                // 回滚
                if (connection != null){
                    connection.rollback();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        } finally {

            JDBCUtils.close(pst1,connection);
            JDBCUtils.close(pst2,null);
        }
    }
}
