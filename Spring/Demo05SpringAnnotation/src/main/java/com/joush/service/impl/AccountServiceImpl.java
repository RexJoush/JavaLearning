package com.joush.service.impl;

import com.joush.dao.AccountDao;
import com.joush.service.AccountService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 账户业务层的实现类
 *
 * 曾经的 xml 配置方法
 * <bean id="accountService" class="com.com.joush.service.impl.AccountServiceImpl"
 *          scope="" init-method="" destroy-method="" >
 *      <property name="" value="" ref=""></property>
 * </bean>
 * 用于创建对象的
 *     作用和在 xml 配置中编写一个 <bean></bean> 标签实现功能一样的
 * 用于注入数据的
 *     作用和在 xml 配置文件中的 bean 标签 写一个 <property></property> 的作用一样的
 * 用于改变作用范围的
 *     作用和在 bean 标签中使用 scope 属性作用一样的
 * 和生命周期相关的
 *     作用和在 bean 标签中使用 init-method 和 destroy-method 属性作用一样的
 */
@Service("accountServiceImpl")
//@Scope("prototype") // 多例延迟创建
@Scope("singleton") // 单例立即创建
public class AccountServiceImpl implements AccountService {


    @Resource(name = "accountDaoImpl")
    private AccountDao accountDao = null;

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化方法");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("销毁方法");
    }
}
