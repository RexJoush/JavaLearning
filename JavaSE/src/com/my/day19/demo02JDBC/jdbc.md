# 各类对象详解
## DriverManger：驱动管理对象
* 注册驱动，告诉程序使用哪一个数据库驱动jar
    ``` java
    static void registerDriver(Driver driver);  // 注册与给定的驱动程序的 DriverManger
    // 写代码使用
    Class.forName("com.mysql.cj.jdbc.Driver");
    // 通过查看源代码发现，在 com.mysql.cj.jdbc.Driver 类中存在静态代码块
    static {
        try{
            java.sql.DriverManager.registerDriver(new Driver());
        } catch(SQLException E){
            throw new RuntimeException("Can't register driver!");
        }
    }
    ```
    - 注意 mysql 5 之后，的驱动 jar 包可以省略注册驱动步骤
* 获取数据库连接
    * 方法
    ``` java
    static Connection getConnection(String url, String username, String password);   
    // 参数
    - url：指定连接的路径
    - 用户名：
    - 密码：
    ```
## Connection：数据库连接对象
* 获取执行 sql 的对象
    ``` java
    Statement createStatement();
    PreparedStatement preparedStatement(String sql);
    ```
* 管理事务
    ``` java
    // 开启事务
    void setAutoCommit(boolean autoCommit); // 调用该方法设置参数为 false，即开启事务
    // 提交事务
    void commit();
    // 回滚
    void rollback();
    ```

## Statement：执行 sql 的对象
* 执行 sql
    ``` java
    boolean execute(String sql); // 可以执行任意的 sql
    int executeUpdate(String sql); // 执行 DML 语句（insert, update, delete）语句，DDL（create, alter, drop）语句
                                   // 返回值，影响的行数，可以通过影响的行数，判断语句是否执行成功
    ResultSet executeQuery(String sql); // 执行 DQL 语句（select）
    ```

## ResultSet：结果集对象，封装查询结果
    - next(); 游标向下移动一行
    - getXxx(): 获取数据
        Xxx 代表数据类型，如 getInt(), getString()等等
        参数
            - int 代表列的编号，代表编号，从 1 开始，getString(1);
            - String 代表列的名称，getString("id");
    - 注意
        使用步骤
            - 游标向下移动一行
            - 判断是否有数据
            - 获取数据
    - 练习
        定义一个方法，查询emp表的所有数据，将其封装为对象，然后装载集合，返回
            - 定义emp类
            - 定义方法获取数据
## PreparedStatement 执行 sql 的对象
    1.sql 注入问题：在拼接 sql 时，有一些 sql 的特殊关键字参与字符串的拼接。会造成安全问题
        - 输入用户名随便，输入密码 a' or 'a' = a'
        - sql: select * from user where username = 'asdhakjfhj' and password = 'a' or 'a' = 'a';
    2.解决 sql 注入问题，使用 PreparedStatement 解决
    3.预编译 sql，使用 ? 作为占位符
    4.注意，后期都会使用 PreparedStatement 来完成
        - 可以防止 sql 注入
        - 效率更高

## 抽取JDBC工具类
    1.目的，简化书写
    2.分析
        1.注册驱动
        2.抽取一个方法
            需求，不想传递参数，还得保证工具类的通用性
            解决方案，配置文件
                jdbc.properties

## JDBC 控制事务
    1.使用 Connection 对象来管理事务
        - 开启事务 setAutoCommit(boolean autoCommit)，调用该方法设置参数为 false，即开启事务
            - 在执行 sql 之前开启事务
        - 提交事务 commit()
            - 当所有 sql 都执行完提交事务
        - 回滚 rollback();
            - 在 catch 中回滚，即发生异常就回滚