## Mybatis 概述
* mybatis 是一个持久层框架，用java编写，封装了jdbc操作的很多细节，使开发者只需要关注sql本身，无需关注其他
* 使用 ORM（Object Relational Mapping, 对象关系映射）思想实现了结果集的封装
* ORM
    - 将数据库表和实体类及实体类的属性对应起来，如我们可以操作实体类就实现操作数据库

## mybatis 的入门
#### mybatis 的环境搭建
* 创建步骤
    - 创建maven工程
    - 创建实体类和dao接口
    - 创建mybatis的主配置文件 SqlMapConfig.xml
    - 创建映射配置文件 UserDao.xml
* 环境搭建的注意事项
    - 创建 UserDao.xml 和 UserDao.java 时名称是为了和我们之前的知识保持一致，在 Mybatis 中，
      把持久层操作接口名称和映射文件也叫做 Mapper，所以 UserDao 和 UserMapper 是一样的
    - 在 IDEA 中创建目录和包是不一样的：包 com.joush.dao是三级目录
    - Mybatis 的映射配置文件必须和 dao 接口的包结构相同
    - 映射配置文件的 mapper 标签的 namespace 属性必须是 dao 接口的全类名
    - 映射配置文件的操作配置，id 属性必须是 dao 接口的方法名

* 项目设计模式讲解
```
/*
    1.读取配置文件

    在读取配置文件的时候绝对路径和相对路径都不可靠，只有两种方法
    1.使用类加载器，但只能读取类路径的配置文件
    2.使用 ServletContext 对象的 getRealPath() 获取路径
*/
InputStream resource = Resources.getResourceAsStream("sqlMapConfig.xml"); 

/*
    2.创建 SqlSessionFactory 工厂
    
    创建工厂时，mybatis 使用了 构建者模式，即 builder 为构建者，resource 可理解为付给构建者报酬
    把对象的创建细节隐藏，使使用者直接调用方法即可拿到对象
*/
SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
SqlSessionFactory factory = builder.build(resource);

/*
    3.使用工厂生产 SqlSession 对象
    
    生产 SqlSession 使用了 工厂模式 ，工厂模式的优点是解耦，降低类之间的依赖关系
*/
SqlSession session = factory.openSession();

/*
    4.使用 SqlSession 创建 Dao 接口的代理对象
    
    Dao接口实现类使用了 代理模式，即在不修改源码的基础上对已有方法进行增强
*/
UserDao dao = session.getMapper(UserDao.class);

// 5.使用代理对象执行方法   
List<User> users = dao.findAll();
for (User user : users){
    System.out.println(user);
}

// 6.释放资源
session.close();
resource.close();

// 注意在映射配置文件中封装实体类，配置方式，指定实体类的全类名

```
* mybatis 基于注解的入门案例
```
把UserDao.xml 移除，在dao接口方法上使用 @Select注解，并指定 sql 语句
同时需要在sqlMapConfig.xml 中的 mapper 配置时，使用 class 属性指定dao 接口的全类名
```
* 明确    
    - 在实际开发中，我们采用不写实现类的方式，不管使用XML还是注解，但 Mybatis 支持写 dao 实现类的 

#### 自定义 mybatis 框架
* 分析
    - mybatis 在使用代理 dao 实现增删改查时，做了两件事
    - 创建代理对象
    - 在代理对象中调用 selectList 方法

* 步骤
    - 根据配置文件的信息创建 Connection 对象，注册驱动获取连接
    - 获取预处理对象 PreparedStatement，此时需要 sql 语句
    - 执行查询 `ResultSet resultSet = preparedStatment.executeQuery(); `
    - 遍历结果集，用于封装
    - 返回 list 集合
* 自定义 mybatis 能通过入门案例看到类
    - class Resource
    - class SqlSessionFactoryBuilder
    - interface SqlSessionFactory
    - interface SqlSession
    

