##案例
####用户登录需求
- 编写 login.html页面
    `username 和 password 两个输入框`
- 使用Druid数据库连接池技术，操作mysql
- 使用JdbcTemplate技术封装JDBC
- 登陆成功跳转到SuccessServlet展示：登陆成功！用户名，欢迎你
- 登录失败跳转到FailServlet展示，登录失败！用户名或密码错误