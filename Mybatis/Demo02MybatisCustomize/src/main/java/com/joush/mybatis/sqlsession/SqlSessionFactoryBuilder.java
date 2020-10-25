package com.joush.mybatis.sqlsession;

import com.joush.mybatis.cfg.Configuration;
import com.joush.mybatis.sqlsession.impl.SqlSessionFactoryImpl;

import java.io.InputStream;

/**
 * 用于创建一个 sqlSessionFactory 对象
 */
public class SqlSessionFactoryBuilder {

    /**
     * 根据参数的字节输入流构建一个 SqlSessionFactory 工厂
     * @param config
     * @return
     */
    public SqlSessionFactory build(InputStream config){

        Configuration configuration = com.joush.mybatis.utils.XMLConfigBuilder.loadConfiguration(config);

        return new SqlSessionFactoryImpl(configuration);

    }

}
