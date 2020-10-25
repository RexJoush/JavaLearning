package com.joush.mybatis.sqlsession.impl;

import com.joush.mybatis.cfg.Configuration;
import com.joush.mybatis.sqlsession.SqlSession;
import com.joush.mybatis.sqlsession.proxy.MapperProxy;
import com.joush.mybatis.utils.DataSourceUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SqlSession接口 的实现类
 */
public class SqlSessionImpl implements SqlSession {

    private Configuration configuration;
    private Connection connection;

    public SqlSessionImpl(Configuration configuration){
        this.configuration = configuration;
        connection = DataSourceUtil.getConnection(configuration);
    }

    /**
     * 用于创建代理对象
     * @param daoInterfaceClass dao的接口字节码文件
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<T> daoInterfaceClass) {

        Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),
                new Class[]{daoInterfaceClass}, new MapperProxy(configuration.getMappers(), connection));
        return null;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
