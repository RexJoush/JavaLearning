package com.joush.mybatis.utils;

import com.joush.mybatis.cfg.Mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 负责执行 SQL 语句，并且封装结果集
 */
public class Executor {

    public <E> List<E> selectList(Mapper mapper, Connection connection){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1.取出 mapper 中的数据
            String queryString = mapper.getQueryString(); // select * from user
            String resultType = mapper.getResultType(); // com.com.com.joush.domain.User
            Class domainClass = Class.forName(resultType);

            // 2.获取 PreparedStatement 对象
            preparedStatement = connection.prepareStatement(queryString);

            // 3.执行 SQL 语句，查询结果集
            resultSet = preparedStatement.executeQuery();

            // 4.封装结果集
            List<E> list = new ArrayList<>();
            while (resultSet.next()){

                // 实例化要封装的对象
                E obj = (E) domainClass.newInstance();

                // 取出元素结果集的原信息
                ResultSetMetaData metaData = resultSet.getMetaData();

                // 取出总列数
                int columnCount = metaData.getColumnCount();

                // 遍历总列数
                for (int i = 0; i < columnCount; i++) {

                    // 获取每列的名称，列名序号从 1 开始
                    String columnName = metaData.getColumnName(1);

                    // 根据列名获取值
                    Object columnValue = resultSet.getObject(columnName);

                    // 给 obj 赋值，使用 Java 内省机制，借助 PropertyDescriptor 实现属性封装
                    PropertyDescriptor descriptor = new PropertyDescriptor(columnName, domainClass);

                    // 获取写入的方法
                    Method writeMethod = descriptor.getWriteMethod();

                    // 把获取的列值，赋值
                    writeMethod.invoke(obj, columnValue);
                }

                // 把赋好值的对象加入集合中
                list.add(obj);
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(preparedStatement, resultSet);
        }
        return null;
    }


    private void release(PreparedStatement preparedStatement, ResultSet resultSet){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
