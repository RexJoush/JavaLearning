##案例
####用户登录需求
- 编写 login.html页面
    `username 和 password 两个输入框`
- 使用Druid数据库连接池技术，操作mysql
- 使用JdbcTemplate技术封装JDBC
- 登陆成功跳转到SuccessServlet展示：登陆成功！用户名，欢迎你
- 登录失败跳转到FailServlet展示，登录失败！用户名或密码错误

##JavaBean:标准的java类，BeanUtils工具类
####要求
- 必须被 public 修饰
- 必须提供空参构造器
- 成员变量必须用 private 修饰
- 提供公共的 setter 和 getter 方法
####功能
- 封装数据
####概念
- 成员变量
    - 属性 setter 和 getter 截取后的产物  
    `getUsername() --> Username --> username`
####方法
- setProperty()
- getProperty()
- populate(Object obj, Map map),将 map 集合的键值对信息封装到对应的JavaBean对象
