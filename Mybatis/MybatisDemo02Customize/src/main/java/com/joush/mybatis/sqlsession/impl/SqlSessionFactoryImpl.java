package com.joush.mybatis.sqlsession.impl;

import com.joush.mybatis.cfg.Configuration;
import com.joush.mybatis.sqlsession.SqlSession;
import com.joush.mybatis.sqlsession.SqlSessionFactory;

/**
 * SqlSessionFactory 接口的实现类
 */
public class SqlSessionFactoryImpl implements SqlSessionFactory {

    private Configuration configuration;

    public SqlSessionFactoryImpl(Configuration configuration){
        this.configuration = configuration;
    }


    /**
     * 用于创建一个新的操作数据库对象
     * @return
     */
    @Override
    public SqlSession openSession() {

        return new SqlSessionImpl(configuration);

    }
}
