## Spring JDBC
    1.Spring 框架对 JDBC 的简单封装，提供了一个 JDBCTemplate 对象简化 JDBC 的开发
    2.步骤
        - 导入 jar 包
        - 创建对象，JDBCTemplate对象，依赖于数据源 DataSource
            JdbcTemplate template = new JdbcTemplate(ds);
        - 调用 JdbcTemplate 的方法来完成 CRUD 操作
            - update(); 执行DML语句，增删改
            - queryForMap(); 查询结果封装为 map 集合
                Map<String, Objcet> map
                注意，只能查询一条结果。key是列名，value是值
            - queryForList(); 查询结果封装为 list 集合
                List<Map<String, Object>> list
                注意，将查询结果的每一条记录封装为 Map 集合，每一个map集合封装为List集合。
            - query(); 查询结果封装为 JavaBean对象
                query方法的参数是 RollMapper
                    使用 BeanPropertyRowMapper 实现类，可以完成数据到 JavaBean 的自动封装
                    new BeanPropertyRowMapper<类型>(类型.class);
            - queryForObject(); 查询结果封装为对象
                                 一般用于聚合函数的查询 