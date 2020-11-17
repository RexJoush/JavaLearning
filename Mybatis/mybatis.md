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
    - 在 IDEA 中创建目录和包是不一样的：包 com.com.com.joush.dao是三级目录
    - Mybatis 的映射配置文件必须和 dao 接口的包结构相同
    - 映射配置文件的 mapper 标签的 namespace 属性必须是 dao 接口的全类名
    - 映射配置文件的操作配置，id 属性必须是 dao 接口的方法名

* 项目设计模式讲解
    ``` java
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
    - 执行查询
        ``` java 
        ResultSet resultSet = preparedStatment.executeQuery(); 
        ```
    - 遍历结果集，用于封装
    - 返回 list 集合
* 自定义 mybatis 能通过入门案例看到的类
    - class Resource
    - class SqlSessionFactoryBuilder
    - interface SqlSessionFactory
    - interface SqlSession

## mybatis 的 crud 操作

* 在 Dao 的接口中定义方法 
``` java
/*
    com.com.com.joush.dao.UserDao.java
*/

// 查询所有
List<User> findAll();

// 保存方法
void saveUser(User user);

// 更新用户
void updateUser(User user);

// 根据 id 删除用户
void deleteUser(int id);

// 根据 id 查询用户
User findById(int id);

// 根据名字模糊查询
List<User> findByName(String username);

// 查询总用户数
int findTotal();
```

* 在对应的 Dao.xml 中配置 sql 语句 
``` xml
<!-- com.com.com.joush.dao.UserDao.xml -->

<!--  查询所有  -->
<select id="findAll" resultType="com.com.com.joush.domain.User">
    select * from user;
</select>

<!--  添加用户  -->
<insert id="saveUser" parameterType="com.com.com.joush.domain.User">
  insert into user (id,username,sex,birthday,address) values (#{id},#{username},#{sex},#{birthday},#{address});
</insert>

<!--  更新用户  -->
<update id="updateUser" parameterType="com.com.com.joush.domain.User">
    update user set username = #{username}, sex = #{sex}, birthday = #{birthday}, address = #{address} where id = #{id}
</update>

<!--  删除用户  -->
<delete id="deleteUser" parameterType="int">
    delete from user where id = #{id};
</delete>

<!--  查询一个  -->
<select id="findById" resultType="com.com.com.joush.domain.User" parameterType="int">
    select * from user where id = #{id};
</select>

<!--  根据名称模糊查询  -->
<select id="findByName" resultType="com.com.com.joush.domain.User" parameterType="String">
    <!--  此方式需要在传参的时候加上 %  
          且，经过查看，此方式通过 PreparedStatement 的 ? 占位符  -->
    select * from user where username like #{username};
    <!--  此方式必须将变量名写为 value
          通过是用 Statement 字符串拼接来实现的  -->
    select * from user where username like '%${value}%';
</select>

<!--  查询总用户数  -->
<select id="findTotal" resultType="int">
    select count(id) from user;
</select>
```

## mybatis 的参数深入和结果集的深入
#### 参数深入
* parameterType （传入类型）
* 传递简单类型
* 传递 pojo 对象
    - Mybatis 使用 ognl 表达式解析对象字段的值，#{} 或 ${} 括号中的值为 pojo 属性名称
    - ognl (Object Graphic Navigation Language, 对象图导航语言)
        - 通过对象中的取值方法获取数据，在写法上省略 get
            ``` java
            // 获取用户的名称
            // 类中的写法
            user.getUsername();
            // ognl 表达式写法
            user.username;
            ```
#### 结果集深入
* 解决实体类名和数据库字段名不一致的问题
    - 起别名
        ``` xml
        <select id="findAll" resultType="com.com.com.joush.domain.User">
        <!--    select * from user; -->
                select id as userId, username as userName, address as userAddress, sex as userSex, birthday as userBirthday from user;
        </select>
        ```
    - 添加单独配置
        ``` xml
        <!--  com.com.com.joush.dao.UserDao.xml  -->
      
        <!--  1.在 mapper 中配置查询结果的列名和实体类的属性名对应关系  -->
        <resultMap id="userMap" type="com.com.com.joush.domain.User">
            <!--  主键  -->
            <id property="userId" column="id"></id>
            <!--  非主键  -->
            <result property="userName" column="username"></result>
            <result property="userSex" column="sex"></result>
            <result property="userAddress" column="address"></result>
            <result property="userBirthday" column="birthday"></result>
        </resultMap>
      
        <!--  2.同时在select标签指定返回结果集合  -->
        <!--  查询所有  -->
        <!-- <select id="findAll" resultType="com.com.com.joush.domain.User"> -->
        <select id="findAll" resultMap="userMap">
            select * from user;
        <!-- select id as userId, username as userName, address as userAddress, sex as userSex, birthday as userBirthday from user; -->
        </select>
        ```
    - PreparedStatement 对象的执行方法
        ``` java
        /* 
            能执行 CRUD 中的任意一种语句，返回值是 boolean 类型，表示是否有结果集
            有结果集是 true，没有是 false 
        */
        boolean execute() throws SQLException;
        
        /*
            只能执行 CUD 操作，无法执行查询，返回结果是影响的行数
        */
        ResultSet executeQuery() throws SQLException;
      
        /*
            只能执行 SELECT 语句，无法执行增删改，执行结果是结果集 ResultSet 对象
        */
        int executeUpdate() throws SQLException;
        ```
## mybatis 中的配置（主配置文件: sqlMapConfig.xml）
#### properties 标签
* 可以将数据库连接信息定义在 properties 标签中
``` xml
<!--  SqlMapConfig.xml  -->

<!--  定义标签，resource 和 url 属性均可，但需要注意
    resource 属性:
        用于指定配置文件的位置，按照类路径填写，必须存在于类路径下
    url 属性:
        要求按照 url 的写法来写地址
        URL: Uniform Resource Locator, 统一资源定位符，可以唯一标识一个资源的位置
            协议      主机    端口  URI
            http://localhost:8080/demo01/demoServlet
        URL: Uniform Resource Identifier, 统一资源标识符，在应用中可以唯一定义一个资源
  -->
<properties resource="jdbcConfig.properties"></properties>
<properties url="file:///D:\a.properties"></properties>

<!--  使用 $ 获取变量值  -->
<dataSource type="POOLED">
    <property name="driver" value="${jdbc.driver}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
</dataSource>
```
``` properties
# jdbcConfig.properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/com.com.joush?serverTimezone=UTC
jdbc.username=root
jdbc.password=password
```
#### typeAliases, package, mappers 标签
* 配置别名，简写对应 dao.xml 中 select, update 等标签的 resultType 值
``` xml
<!--  SqlMapConfig.xml  -->

<typeAliases>   
    <!--  typeAlias 用于配置别名，type 表示 domain 下的全类名，alias 属性指定别名，当指定了别名，就不再区分大小写  -->
    <typeAlias type="com.com.com.joush.domain.User" alias="user"></typeAlias>

    <!--  package 用于指定配置别名的包，当指定之后，该包下的实体类都会注册别名，类名即别名，不再区分大小写  -->
    <package name="com.com.com.joush.domain"/>

</typeAliases>

<mappers>
    <!--  package标签用于指定 dao 接口所在的包，当指定了之后，就不需要写 mapper 以及 resource 或 class 了  -->
    <!--  <mapper resource="com/com.com.joush/dao/UserDao.xml"></mapper>  -->
    <package name="com.com.com.joush.dao"></package>
</mappers>

<!--  com.com.com.joush.dao.UserDao.xml  -->

<!--  查询所有  -->
<select id="findAll" resultType="user">
    select * from user;
</select>
``` 

## Mybatis 的连接池及事务

#### 连接池
* 连接池，就是一个用于存储连接的容器
    - 容器就是一个对象，该集合必须是线程安全的，不能两个线程拿到同一个连接
    - 该集合还必须实现队列的特性，先进先出
* mybatis 连接池
    - mybatis 连接池提供了3中配置方式
        - 配置位置
            - SqlMapConfig.xml 中的 DataSource 标签，type 属性表示采用何种连接池方式
        - type 属性的取值
            - POOLED, 采用传统的 javax.sql.DataSource 规范中的连接池，mybatis 中有针对规范的实现
            - UNPOOLED, 采用传统的获取连接的方式，实现了 javax.sql.DataSource 接口，但并没有池的思想
            - JNDI, 采用服务器提供的 JNDI 技术实现，来获取 DataSource 对象，不同服务器拿到的 DataSource 不一样，注意，不是 web 或 maven 工程，不能使用
            - 使用 tomcat 服务器，采用连接池为 dbcp 连接池
* 连接池连接的获取思想
    ``` text
    mybatis 中有两个池子，空闲池和活动池，当想要获取一个连接时，首先去空闲池中拿
        如果空闲池为空，直接返回连接
        如果空闲池非空，则查看活动池中是否已经达到最大数量，达到了最大数量，则把创建最早的连接收回，并返回
    ```

#### 事务
* mybatis通过 SqlSession 对象的 commit 方法和 rollback 方法实现事务的提交和回滚
``` java
// 在 openSession 方法传参即可实现事务的自动提交
sqlSession = factory.openSession(true);

// openSession 方法定义
SqlSession openSession();
SqlSession openSession(boolean autoCommit);
```

## Mybatis 映射文件的 SQL 深入, 动态 SQL

#### 使用 if where sql 标签
* 在配置文件中写入配置
``` xml java
<!--  com.com.com.joush.dao.UserDao.xml  -->

<!--  根据条件查询  -->
<select id="findUserByCondition" resultType="user" parameterType="user">
    select * from user where 1 = 1

    <!--  根据是否有参数来进行拼接 sql  -->
    <if test="username != null">
        and username = #{username}
    </if>

    <if test="sex != null">
        and sex = #{sex}
    </if>
</select>

<!--  加入 where 标签  -->
<select id="findUserByCondition" resultType="user" parameterType="user">
    select * from user
    <where>
        <if test="username != null">
            and username = #{username}
        </if>
        <if test="sex != null">
            and sex = #{sex}
        </if>
    </where>
</select>
```
* 测试函数
``` java
// 可以根据两个条件查询，或者传入单条件查询
public void testFindByCondition(){
    User user = new User();
    user.setUsername("Joush");
    user.setSex("男");
    
    // 5.根据条件查询
    List<User> users = userDao.findUserByCondition(user);

    // 6.输出结果
    for (User u : users) {
        System.out.println(u);
    }
}
```
* 可以使用 sql 标签来抽取重复的 sql 语句
``` xml
<!--  com.com.com.joush.dao.UserDao.xml  -->

<!--  抽取重复的 sql 语句  -->
<sql id="defaultUser">
    select * from user;
</sql>

<!--  查询所有  -->
<select id="findAll" resultType="user">
    <include refid="defaultUser"></include>
</select>

<!--  查询一个  -->
<select id="findById" resultType="user" parameterType="int">
    <include refid="defaultUser"></include>
    where id = #{id};
</select>
```
#### 使用 foreach 标签实现集合查询
* QueryVo 的定义
``` java
// com.com.com.joush.domain.QueryVo.java
private List<Integer> ids;
public List<Integer> getIds() {
        return ids;
}

public void setIds(List<Integer> ids) {
    this.ids = ids;
}
```
* xml中的配置
``` xml
<!--  com.com.com.joush.dao.UserDao.xml  -->

<!--  根据 queryVo中的集合实现查询用户列表  -->
<select id="findUserInIds" resultType="user" parameterType="queryVo">
    select * from user
    <where>
        <!--  写入集合，列表参数存在，且长度大于0  -->
        <if test="ids != null and ids.size() > 0">
            <!--  使用 foreach 标签来实现
                collection  传参的集合名
                open        sql 的开始字符串
                close       sql 的结束字符串
                item        数据项的名字，和标签内容的#{id}对应
                separator   分割符
             -->
            <foreach collection="ids" open="and id in (" close=")" item="id" separator=",">
                #{id}
            </foreach>
        </if>
    </where>
</select>
```

## Mybatis 的多表查询

#### 一对一关系
* 介绍
``` text
用户和账户
一个用户有一个账户，有两张表，用户表和账户表
通过查询账户信息，获得当前账户的用户信息
```
* 首先将 domain 文件进行修改，账户表添加用户属性
``` java
// com.com.com.joush.domain.Account.java
private int id;
private int uid;
private double money;

// 从表实体类应该包含一个主表实体类的对象引用
private User user;

// 重新生成 get set 方法
```
* 在对应的配置文件中添加结果集映射
``` xml
<!--  com.com.com.joush.dao.AccountDao.xml  -->

<!--  带一封装 account 和 user 的resultMap  -->
<resultMap id="accountUserMap" type="account">
    <id property="id" column="aid"></id>
    <result property="uid" column="uid"></result>
    <result property="money" column="money"></result>

    <!--  一对一的关系映射，配置封装 user 的内容，使用 association 标签  -->
    <association property="user" column="uid" javaType="user">
        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="address" column="address"></result>
        <result property="sex" column="sex"></result>
        <result property="birthday" column="birthday"></result>
    </association>
</resultMap>

<!--  修改sql 语句  -->
<!--  查询所有  -->
<select id="findAll" resultMap="accountUserMap">
    select u.*,a.aid as aid, a.uid,a.money from account a, user u where u.id = a.uid;
</select>
```
* 测试
``` java
// com.com.com.joush.AccountTest.java
@Test
public void testFindAll() throws IOException {
    // 5.执行查询所有方法
    List<Account> accounts = accountDao.findAll();
    for (Account account : accounts) {
        System.out.println(account);
        System.out.println(account.getUser());
    }

}
```

#### 一对多关系
* 介绍
``` text
一个用户可能有多个账户
通过查询用户的信息，同时获取该用户的所有账户信息
```
* 添加 list 属性
``` java
com.com.com.joush.domain.User.java

private int id;
private String username;
private String address;
private String sex;
private String birthday;

// 一对多关系映射，主表实体类应该包含从表实体的集合引用
private List<Account> accounts;

// 重新生成 get set 方法
```
* 修改 xml 配置
``` xml
<!--  com.com.com.joush.dao.UserDao.xml  -->

<!--  定义 User 的 resultMap  -->
<resultMap id="userAccountMap" type="user">
    <id property="id" column="id"></id>
    <result property="username" column="username"></result>
    <result property="address" column="address"></result>
    <result property="sex" column="sex"></result>
    <result property="birthday" column="birthday"></result>

    <!--  配置 user 对象中 accounts 集合的映射，一对多，使用 collection 标签  -->
    <collection property="accounts" ofType="account">
        <!--  此处 id 注意区分  -->
        <id column="aid" property="id"></id>
        <result column="uid" property="uid"></result>
        <result column="money" property="money"></result>
    </collection>
</resultMap>

<!--  修改sql 语句  -->
<!--  因为要查出所有的账户信息，即使没有，所以使用左外连接，保留左表所有字段，此处查询结果中如果一个user 拥有多个 account 
      mybatis 会自动将重复字段封装到一个 id 的 accounts 集合中  -->
<select id="findAll" resultMap="userAccountMap">
    select * from user u left outer join account a on u.id = a.uid;
</select>
```
* 测试
``` java
// com.com.com.joush.UserTest.java
@Test
public void testFindAll() throws IOException {
    // 5.执行查询所有方法
    List<User> users = userDao.findAll();
    for (User user : users) {
        System.out.println(user);
        System.out.println(user.getAccounts());
    }
}
```
#### 多对多
* 介绍
``` text
用户和角色
用户可以有多个角色，即一个人有很多重身份。 学生，子女
同时一个角色也可以赋予多个用户。即很多人都是学生或子女 

在数据库中，想要实现多对多的关系，需要使用中间表，中间表包含各自的主键，在中间表中是外键

实现配置
    查询用户时，可以获得用户包含的角色信息
    查询角色时，可以获得所有用户
```
* 修改 xml 配置
``` xml
<!--  com.com.com.joush.dao.RoleDao.xml  -->

<!--  定义 role 表的 resultMap  -->
<resultMap id="roleMap" type="role">
    <id property="roleId" column="id"></id>
    <result property="roleName" column="role_name"></result>
    <result property="roleDesc" column="role_desc"></result>
    <collection property="users" ofType="user">
        <id property="id" column="id"></id>
        <id property="username" column="username"></id>
        <id property="address" column="address"></id>
        <id property="sex" column="sex"></id>
        <id property="birthday" column="birthday"></id>
    </collection>
</resultMap>

<!--  查询所有，用户的角色信息,注意表的连接，第一个左外连接的结果和第二个表进行左外连接  -->
<select id="findAll" resultMap="roleMap">
    select u.*,r.id as rid, r.role_name, r.role_desc from role r
    left join user_role ur on r.id = ur.rid
    left join user u on u.id = ur.uid;
</select>

<!--  com.com.com.joush.dao.UserDao.xml  -->
<resultMap id="userMap" type="user">
    <id property="id" column="id"></id>
    <result property="username" column="username"></result>
    <result property="address" column="address"></result>
    <result property="sex" column="sex"></result>
    <result property="birthday" column="birthday"></result>

    <!--  配置角色集合的映射  -->
    <collection property="roles" ofType="role">
        <id property="roleId" column="rid"></id>
        <result property="roleName" column="role_name"></result>
        <result property="roleDesc" column="role_desc"></result>
    </collection>
</resultMap>

<!--  查询所有，和上面的连接语句类似  -->
<select id="findAll" resultMap="userMap">
    select u.*,r.id as rid, r.role_name, r.role_desc from user u
    left join user_role ur on u.id = ur.uid
    left join role r on r.id = ur.rid;
</select>
```

## Mybatis 的延迟加载
* 在一对多中，一个用户有100个账户，是否需要把关联账户查出？
    - 在查询账户时，用户下的账户信息应该是什么时候使用，什么时候查询的
* 查询账户时，是否需要把用户的关联信息查询出来？
    - 在查询账户时，账户所属用户信息，应该是随着账户信息一起查询出来的

* 延迟加载
    - 在真正使用数据时才发起查询，不用的时候不查询，按需加载
* 立即加载
    - 不管用不用，只要调用方法

#### 一对一
* 修改 accountDao 文件
``` xml
<!--  com.com.com.joush.dao.AccountDao.xml  -->

<!--  带一封装 account 和 user 的resultMap  -->
<resultMap id="accountUserMap" type="account">
    <id property="id" column="id"></id>
    <result property="uid" column="uid"></result>
    <result property="money" column="money"></result>

    <!--  一对一的关系映射，配置封装 user 内容
          select 属性指定的内容，查询用户的唯一标志
          column 属性指定的内容，用户根据 id 查询时，所需要的参数值
            -->
    <association property="user" column="uid" javaType="user" select="com.com.com.joush.dao.UserDao.findById"></association>
</resultMap>

<!--  查询所有 -->
<select id="findAll" resultMap="accountUserMap">
    select * from account
</select>
```
* 修改 mybatis 主配置文件
> 配置方法见 <https://mybatis.org/mybatis-3/zh/configuration.html#settings>
``` xml
<!--  SqlMapConfig.xml  -->

<!--  配置 mybatis 的参数  -->
<settings>
    <!--  开启 mybatis 支持延迟加载  -->
    <setting name="lazyLoadingEnabled" value="true"/>
    <setting name="aggressiveLazyLoading" value="false"/>
</settings>
```
#### 一对多
* 修改 userDao 文件
``` xml
<!--  com.com.com.joush.dao.userDao.xml  -->

<!--  定义 User 的 resultMap  -->
    <resultMap id="userAccountMap" type="user">
        <id property="id" column="id"></id>
        <result property="username" column="username"></result>
        <result property="address" column="address"></result>
        <result property="sex" column="sex"></result>
        <result property="birthday" column="birthday"></result>
        <!--  此处的 column 是指通过 user 的 id 来查询，所以列名为 id  -->
        <collection property="accounts" ofType="account" select="com.com.com.joush.dao.AccountDao.finAccountByUid" column="id"></collection>
    </resultMap>

<!--  查询所有 -->
<select id="findAll" resultMap="accountUserMap">
    select * from account
</select>


```
* 修改 Account 相关文件
``` java
// com.com.com.joush.dao.AccountDao.java

// 添加通过 id 查询的方法
/**
 * 根据用户 id 查询用户信息
 * @param uid
 * @return
 */
List<Account> finAccountByUid(int uid);
```
``` xml
<!--  com.com.com.joush.dao.AccountDao.xml -->

<!--  根据用户 id 查询账户列表  -->
<select id="finAccountByUid" resultType="account">
    select * from account where uid = #{uid};
</select>
```
* 最后一步同上，开启主配置文件中的加载选项

## Mybatis 缓存

#### 缓存的基本概念
* 什么是缓存
    - 存在内存中的临时数据
* 为什么使用缓存
    - 减少数据库交互，提高执行效率
* 什么样的数据能使用缓存
    - 适用于缓存
        - 经常查询且不长改变
        - 数据的正确与否对最终结果影响不大的
    - 不适用缓存
        - 经常改变的数据
        - 数据的正确与否对最终结果影响很大
        - 如，商品的库存，银行的汇率，股市的牌价等
#### mybatis 的一级缓存
* 指的是 Mybatis 中的 SqlSession 对象的缓存
    ``` text
    当执行查询后，查询结果会同时存入 SqlSession 提供的区域中，该区域结构为 Map 集合，当再次查询相同的数据，Mybatis 会先去 SqlSession 中查询，看是否有，有就直接返回
    当 SqlSession 对象消失后，一级缓存消失
    ```
* 测试
``` java 

// 注意修改userDao.xml和UserDao.java的相关配置

@Test
public void testFirstLevelCache(){

    User user1 = userDao.findById(41);
    System.out.println(user1); // com.com.com.joush.domain.User@b968a76

    sqlSession.clearCache(); // 此方法可以清空缓存，当清空时，缓存即消失，两个对象不再相等

    User user2 = userDao.findById(41);
    System.out.println(user2); // com.com.com.joush.domain.User@b968a76

    System.out.println(user1 == user2); // true
}
```
* 一级缓存的分析
    - 一级缓存是 SqlSession 范围的缓存，当调用 SqlSession 的修改，添加，删除，commit(), close() 等方法时，一级缓存自动清空
    
#### mybatis 的二级缓存
* 指的是 SqlSessionFactory 对象的缓存
``` text
该缓存由同一个 SqlSessionFactory 对象创建的 SqlSession 共享二级缓存
```
* 使用步骤
    - 让 mybatis 支持二级缓存
    ``` xml
    <!--  sqlMapConfig.xml  -->
    
    <settings>
        <setting name="cacheEnabled" value="true"/>
    </settings>
    ```
    - 让当前的映射文件支持二级缓存
    ``` xml
    <!--  com.com.com.joush.dao.UserDao.xml  -->
    
    <!--  开启支持二级缓存  -->
    <mapper namespace="com.com.com.joush.dao.UserDao">
        <cache/>
    </mapper>
    ```
    - 让当前的操作支持二级缓存
    ``` xml
    <!--  com.com.com.joush.dao.UserDao.xml  -->
    
    <!--  添加 userCache 属性，置为 true  -->
    <!--  查询一个  -->
    <select id="findById" resultType="user" parameterType="int" useCache="true">
        select * from user where id = #{uid};
    </select>
    ```
* 二级缓存分析
    - 二级缓存存放的是数据，而不是对象，所以两次获取的不是同一个对象
    
## mybatis 注解开发
* **注意，如果写了注解，同时还存在xml文件，那么无论使不使用 xml，mybatis 都会报错，所以建议统一为注解或者 xml**
#### 在 dao 的方法上添加对应的注解和 sql 语句即可
``` java
// com.com.com.joush.dao.UserDao.java

/**
 * 查询所有用户
 * @return
 */
@Select("select * from user")
List<User> findAll();
```

#### mybatis 的单表 CRUD 操作
``` java
// com.com.com.joush.dao.UserDao.java
/**
 * 查询所有用户
 * @return
 */
@Select("select * from user")
List<User> findAll();

/**
 * 保存用户
 * @param user
 */
@Insert("insert into user (id, username, address, sex, birthday) values (#{id},#{username},#{address},#{sex},#{birthday})")
void saveUser(User user);

/**
 * 更新用户
 * @param user
 */
@Update("update user set username = #{username}, address = #{address}, sex = #{sex}, birthday = #{birthday} where id = #{id}")
void updateUser(User user);

/**
 * 删除用户
 * @param id
 */
@Delete("delete from user where id = #{id}")
void deleteUser(int id);

/**
 * 查询一个
 * @param id
 * @return
 */
@Select("select * from user where id = #{id}")
User findById(int id);

/**
 * 根据名字模糊查询
 * @return
 */
@Select("select * from user where username like #{username}")
List<User> findByName();

/**
 * 查询总用户数量
 * @return
 */
@Select("select count(*) from user")
int findTotal();
```

#### mybatis 注解建立实体类和数据库列名之间的对应关系
* 使用 Results 和 Result 注解
``` java
/**
 * 查询所有用户
 * @return
 */
@Select("select * from user")
// 此处的 id 属性表示，可以在别的方法使用此注解，使用方法如 查询一个方法
@Results(id = "userMap", value = {
        @Result(id = true, column = "id", property = "userId"),
        @Result(column = "username", property = "userName"),
        @Result(column = "address", property = "userAddress"),
        @Result(column = "sex", property = "userSex"),
        @Result(column = "birthday", property = "userBirthday"),
})

/**
 * 查询一个
 * @param id
 * @return
 */
@Select("select * from user where id = #{id}")
@ResultMap(value = {"userMap"})
@ResultMap("userMap") // 只有一个 value 值，value 可省略，数组只有一个值，{} 也可省略
User findById(int id);
```
#### mybatis 的多表操作
* 一对一，使用 one 注解
``` java
// com.com.com.joush.domain.Account.java

// 多对一的映射，一个账户属于一个用户
private User user;

// com.com.com.joush.dao.AccountDao.java
/**
 * 查询所有账户，并获取每个账户所属的用户信息
 * @return
 */
@Select("select * from account")
@Results(id="accountMap",value={
        @Result(id = true, column = "id", property = "id"),
        @Result(column = "uid", property = "uid"),
        @Result(column = "money", property = "money"),
        /*
            给 User 属性添加 one 注解，指定id为查询字段，全类名.方法名
            同时 FetchType.EAGER 表示立即加载，FetchType.LAZY 为懒加载
            一般一对一为立即加载，一对多为懒加载
        */
        @Result(property = "user", column = "uid", one = @One(
                select = "com.com.com.joush.dao.UserDao.findById",fetchType = FetchType.EAGER
        ))
})
List<Account> findAll();
```
* 一对多，使用 many 注解
``` java
// com.com.com.joush.domain.User.java
private List<Account> accounts;

// com.com.com.joush.dao.UserDao.java

/**
 * 查询所有用户
 * @return
 */

@Select("select * from user")
@Results(id = "userMap", value = {
        @Result(id = true, column = "id", property = "userId"),
        @Result(column = "username", property = "userName"),
        @Result(column = "address", property = "userAddress"),
        @Result(column = "sex", property = "userSex"),
        @Result(column = "birthday", property = "userBirthday"),
        /*
            注意，此处需要给 AccountDao添加一个根据 id 查询账户的方法，才能执行
        */
        @Result(column = "id", property = "accounts", many = @Many(
            select = "com.com.com.joush.dao.AccountDao.findById", fetchType = FetchType.LAZY
        ))
})
List<User> findAll();

// com.com.com.joush.dao.AccountDao.java
/**
     * 根据 id 查询一个
     * @param id
     * @return
     */
    @Select("select * from account where uid = #{id}")
    Account findById(int id);
```

* 注解中使用 mybatis 的缓存
    - 一级缓存默认开启，所以不需要关注
    - 二级缓存开启方法
    ``` java 
    // com.com.com.joush.dao.UserDao.java
    
    // 在类上添加 CacheNamespace 注解，并设置 blocking 属性为 true，即表示开启二级缓存
    @CacheNamespace(blocking = true)
    public interface UserDao {
        // ...
    }
    ```