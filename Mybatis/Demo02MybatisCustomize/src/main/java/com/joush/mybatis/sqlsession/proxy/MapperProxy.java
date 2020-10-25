package com.joush.mybatis.sqlsession.proxy;

import com.joush.mybatis.cfg.Mapper;
import com.joush.mybatis.utils.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

public class MapperProxy implements InvocationHandler {


    // map 的 key 是全类名.方法名
    private Map<String, Mapper> mappers;
    private Connection connection;

    public MapperProxy(Map<String, Mapper> mappers, Connection connection){
        this.mappers = mappers;
        this.connection = connection;
    }

    /**
     * 用于对方法进行增强，调用 selectList 方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 1.获取方法名
        String methodName = method.getName();

        // 2.获取方法所在类的名称
        Class<?> className = method.getDeclaringClass();

        // 3.组合 key
        String key = className + "." + methodName;

        // 4.获取 mappers 中的 mapper 对象
        Mapper mapper = mappers.get(key);

        // 5.判断一下是否有 mapper
        if (mapper == null){
            throw new IllegalArgumentException("参数有误");
        }

        // 6.调用工具类，执行查询所有
        return new Executor().selectList(mapper, connection);
    }
}
