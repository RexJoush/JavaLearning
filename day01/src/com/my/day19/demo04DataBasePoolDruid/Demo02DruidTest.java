package com.my.day19.demo04DataBasePoolDruid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用工具类进行测试
 */
public class Demo02DruidTest {
    public static void main(String[] args) {
        /*
            完成加载操作，给 account 添加一条记录
         */
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // 1.获取连接
            connection = JDBCUtils.getConnection();
            // 定义 sql
            String sql = "insert into account values (?,?,?)";

            // 获取 PreparedStatement 对象
            preparedStatement = connection.prepareStatement(sql);

            // 给 ? 赋值
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "lisi");
            preparedStatement.setString(3, "123");

            // 执行 sql
            int i = preparedStatement.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(preparedStatement, connection);
        }
    }
}
