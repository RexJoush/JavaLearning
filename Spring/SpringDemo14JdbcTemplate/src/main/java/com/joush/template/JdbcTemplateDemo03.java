package com.joush.template;


import com.joush.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * JdbcTemplate 的 CRUD 操作
 */
public class JdbcTemplateDemo03 {

    public static void main(String[] args) {

        // 1.获取容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");

        // 2.获取对象
        JdbcTemplate template = ac.getBean("jdbcTemplate", JdbcTemplate.class);

        // 3.执行操作

        // 保存
        // template.update("insert into new_account (name, money) values (?,?)", "fff",1000d);

        // 更新
        // template.update("update new_account set money = ? where id = ?", 2000d, 6);

        // 删除
        // template.update("delete from new_account where id = ?", 6);

        // 查询所有
        // List<Account> accounts = template.query("select * from new_account", new AccountRowMapper());
//        List<Account> accounts = template.query("select * from new_account", new BeanPropertyRowMapper<Account>(Account.class));
//        for (Account account : accounts){
//            System.out.println(account);
//        }

        // 查询一个
        List<Account> accounts2 = template.query("select * from new_account where id = ?", new BeanPropertyRowMapper<Account>(Account.class), 1);
        System.out.println(accounts2.isEmpty()? "没有结果" : accounts2.get(0));

        // 查询返回一行一列（使用聚合函数，不加 group by 子句）
        long integer = template.queryForObject("select count(*) from new_account where money > ?", Long.class, 900d);
        System.out.println(integer);
    }
}

/**
 * 定义 Account 的封装策略
 */
class AccountRowMapper implements RowMapper<Account> {

    /**
     * 把结果集中的数据封装到 Account 中，然后由 spring 把每个 Account 加入集合中
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {

        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setName(resultSet.getString("name"));
        account.setMoney(resultSet.getDouble("money"));

        return account;

    }
}
