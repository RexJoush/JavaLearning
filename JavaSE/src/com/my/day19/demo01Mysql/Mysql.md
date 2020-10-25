## DDL (Data Definition Language) 数据定义语言
#### 操作数据库：CRUD
* C Creat(创建)
    - 创建数据库
        ``` sql
        -- 创建名为 my_database 的数据库
        create database my_database;
        ``` 
    - 创建数据库，判断是否存在，再创建
        ``` sql
        -- 如果不存在，创建名为 my_database 的数据库
        create database if not exists my_database; 
        ```
    - 创建数据库，并指定字符集
        ``` sql
        -- 创建名为 my_database 的数据库，指定字符集为 utf8
        create database my_database set character utf8; 
        ```
    - 练习
        ``` sql
        -- 创建名为 my_database 的数据库（如果不存在），设置默认字符集为 utf-8
        create database if not exists my_database character set utf8; 
        ```
             
* R Retrieve(查询)
    ``` sql
    -- 查询所有数据库的名称
    show databases; 
    -- 查询名字为 mysql数据库的创建语句
    show create database mysql; 
    ```
        
* U Update(修改)
    ``` sql
    -- 修改数据库的字符集
    alter database my_database character set utf8; 将数据库 my_database 字符集改为 utf8 
    ```
        
* D Delete(删除)
    - 删除数据库
        ``` sql
        -- 删除 my_database 数据库
        drop database my_database; 
        ```
    
    - 删除数据库，判断是否存在
        ``` sql
        -- 如果存在，再删除 my_database 数据库
        drop database if exists my_database; 
        ```
     
    - 使用数据库
        ``` sql
        -- 查询当前正在使用的数据库
        select database(); 查询正在使用的数据库
        -- 使用数据库
        use my_database; 进入 my_database 数据库
        ```

#### 操作表：CRUD
* C Creat(创建)
    - 创建表
        ``` sql
        create table my_table(
            col1 data_type1,
            col2 data_type2,
            ...
            coln data_typen
        );
        ```
    - 注意： 
        - 最后一列不加 **,**
        - 数据库类型
        ``` sql
        - int --整型 
            age int,
        - double -- 浮点型
            socre double(5,2), -- 五位长度，两位小数
        - date -- 日期，只包含年月日
            yyyy-MM-dd
        - datetime -- 日期，包含年月日时分秒
            yyyy-MM-dd HH:mm:ss
        - timestamp -- 时间戳类型，包含年月日时分秒
            yyyy-MM-dd HH:mm:ss, -- 如果不给这个字段赋值，或赋值为 null，则默认使用当前系统时间，来自动赋值
        - varchar() -- 可变长字符串
            name varchar(10), -- 最大长度为 10 的字符串
        ```
    - test
        ``` sql
        create table student(
            id int,
            name varchar(32),
            age int,
            score double(4,1),
            borth date,
            insert_time timestamp
        );  -- 创建学生表
        ```
    - 复制表
        ``` sql
        -- 创建一个和 my_table 一样的表 my_table1
        create table my_table1 like my_table; 
        ```
    
* R Retrieve(查询)
    - 查询数据库的所有表的名称
        ``` sql
        -- 查询所有表名称
        show tables; 
        ```
    - 查询表结构
        ``` sql
        -- 查询名为 my_table 的表结构
        desc my_table; 
        ```
* U Update(修改)
    - 修改表名
        ``` sql
        --将 my_table 表修改名字为 new_table
        alter table my_table rename to new_table;
        ```
    - 修改表的字符集
        ``` sql
        -- 查看 my_table 表的字符集
        show create table my_table;
        -- 将 my_table 的字符集改为 utf8
        alter table my_table character set utf8;
        ```
    - 添加一列
        ``` sql
      -- 给 my_table 添加一列，名为 col_name，数据类型为 data_type
        alter table my_table add col_name data_type;
        ```
    - 修改列的类型、名称
        - 修改列的名称和类型
        ``` sql
        -- 将 my_table 表的 col_name 列改为 new_name，数据类型是 data_type
        alter table my_table change col_name new_name data_type;
        ```
        - 修改列的类型
        ``` sql
        -- 将 my_table 表的 col_name 列类型改为 data_type
        alter table my_table modify col_name data_type; 
        ```
    - 删除列
        ``` sql
        -- 删除 my_table 表中的 col_name 列
        alter table my_table drop col_name;
        ```
* D Delete(删除)
    ``` sql
    -- 删除 my_table 表
    drop table my_table; 
    -- 如果存在，则删除名为 my_table 的表
    drop table if exists my_table; 
    ```

## DML (Data Manipulation Language) 数据操纵语言
#### 增删改表中的数据
* 添加数据
    
    - 语法
    ``` sql
    insert into my_table (col_name1, col_name2, col_name3, ..., col_namen) values (value1, value2, value3, ... valuen);
    ```
    - 注意事项
        - 列名和值名需要一一对应
        - 如果表名后不定义列名，默认给所有列添加
        - 除了数字类型，其他类型需要使用 '' 引起来，'' "" 均可
* 删除数据

    - 语法
        ``` sql
        delete from my_table [where if];
        -- 删除 my_table 表，创建一个新的一样的。即达成清空表的操作
        truncate table my_table; 
        ```
    - 注意事项
        - 如果不加条件，就删除所有记录
        - 删除所有记录
        ``` sql
        delete from my_table; -- 执行 n 条 delete 语句，不建议
        truncate table my_table; -- 效率更高
        ```
* 修改数据

    - 语法
    ``` sql
    update my_table set col_name1 = value1, col_name2 = value2, ..., col_namen = valuen [where if];
    ```
    - 注意事项
        - 如果不加条件，那么会把表中所有记录修改
        
    

## DQL (Data Query Language) 数据查询语言
#### 查询表中的数据
* 语法
    ``` sql
    select col_name1, col_name2, col_name3, ..., col_namen   -- 查找列名
    from my_table1, my_table2,... my_tablen        -- 从某某表中查询
    where if1, if2, ... , ifn       -- 查询条件
    group by col_name       -- 分组字段
    having manipulation     -- 分组之后的条件
    order by ASC/DESC       -- 排序
    limit div_page          -- 分页
    ```
    
* 基础查询 
    - 多个字段的查询
        ``` sql
        select col_name1, col_name2, col_name3, ..., col_namen from my_table1;
        ```
    - 去出重复
        ``` sql
        distinct -- 在 select 后加上 distinct 表示去除相同值
        ```
    - 计算列
        ``` sql
        一般可以使用四则运算计算一些列的值，（一般只会进行数值型的计算）
        - ifnull (表达式1, 表达式2)，null 参与计算的结果都为 null
            - 表达式1，哪个字段需要判断是否为 null，
            - 如果该字段为 null，此处写 null 替换后的值
        ```
    - 起别名
        ``` sql
        as, as也可省略
        ```
* 条件查询
    - where 字句后跟的条件
    - 运算符
        ``` sql
        - * > < <= >= = <> 
                - between... and
                - in (集合)
                - like
                - is null
                - and 或 && 
                - or 或 ||
                - not 或 !
            - in (集合)
            - like -- 模糊查询
                - 占位符
                    - _ 单个任意字符
                    - % 多个任意字符
        ```
* 排序查询
    - 语法: order by 子句
        ``` sql
        order by 排序字段1 排序方式1， 排序字段2 排序方式2...
        ```
    - 排序方式
        ``` sql
        ASC -- 升序，默认的
        DESC -- 降序 
        ```
    - 注意
        - 如果有多个排序条件，只有当前面的条件值一样，才会判断第二条件
* 聚合函数 -- 将一列数据作为一个整体，进行纵向计算，
    ``` sql
    count -- 计算数量
    max -- 计算最大值
    min -- 计算最小值
    avg -- 计算平均值
    ```
    - 注意，聚合函数的计算，排除 null 值
        - 解决方案
            - 尽量选择非空的列进行计算
            - ifnull函数
* 分组查询 
    - 语法
    ``` sql
    - group by 子句
    ```
    - 注意
    ``` sql
    - 分组之后查询的字段，分组字段，聚合函数
    - where 和 having 的区别
        - where 在分组之前进行限定，如果不满足条件，则不参与分组。
        - having 在分组之后进行限定，如果不满足结果，则不会被查询出来。
    ```
* 分页查询
    - 语法 limit 开始的索引,每页查询的条数
    - 公式: 开始的索引 = (当前的页码 - 1) * 每页显示的条数
    - limit 是一个mysql的"方言"，即仅在mysql中使用。

## DCL (Data Control Language) 数据控制语言
#### 管理用户
* 创建用户
    ``` sql
    create user '用户名'@'主机名' identified by 'password';
    create user 'zhangsa'@'localhost' identified by '123'; -- 本机登录
    create user 'zhangsa'@'%' identified by '123'; -- 所有远程可登录
    ```
* 查询用户
    ``` sql
    -- 切换到 mysql 数据库
    use mysql;
    -- 查询 user 表
    select * from user;
    ```
* 删除用户
    ``` sql
    drop user '用户名'@'主机名';
    drop user 'zhangsa'@'localhost';
    drop user 'zhangsa'@'%';
    ```
* 修改密码
    ``` sql
    update user set password =  password('password') where user = 'username';    -- password();加密函数    
    update user set password = password('123') where user = 'lisi';  -- 修改李四的密码为123
    set password for 'username'@'hostname' = password('password');
     
    -- mysql 中，忘记了 root 用户的密码
        -- 1.cmd --> net stop mysql -- 停掉 mysql 服务
        -- 2.使用无验证方式启动 mysql mysqld --skip-grant-tables
        -- 3.打开新的 cmd 启动 mysql 进行修改即可
        -- 4.任务管理器关闭 mysqld 服务
    ```

#### 权限管理
* 查询权限
    ``` sql
    show grants for 'username'@'hostname';
    show grants for 'lisi'@'localhost';
    ```
* 授予权限
    ``` sql
    grant 权限列表 on 数据库名.表名 to 'username'@'hostname';
    grant select,delete on db1.account to 'lisi'@'localhost'; -- 给李四授予 db1数据库下的 account 表的查询，删除权限
    grant all on *.* to 'zhangsan'@'localhost'; -- 给张三授予所有数据库所有表的所有权限
    ```
* 撤销权限
    ``` sql
    revoke 权限列表 on 数据库名.表名 from 'username'@'hostname';
    revoke select on db1.account to 'lisi'@'localhost'; -- 撤销李四 db1数据库下的 account 表的查询权限
    ```
    
## 约束
#### 概念
* 对表中的数据进行限定，保证数据的正确性、有效性和完整性

#### 分类：
* 主键约束: primary key
* 非空约束: not null
* 唯一约束: unique
* 外键约束: foreign key
* 非空约束: not null
    - 创建表时添加约束
    ``` sql
    create table stu (
        id int,
        name varchar(20) not null --name为非空
    );
    ```
    - 删除非空约束
    ``` sql
    alter table stu modify name varchar(20);
    ```
    - 创建表后添加非空约束
    ``` sql
    alter table stu modify name varchar(20) nut null;
    ```
* 唯一约束: unique
    - 创建表时添加唯一约束
    ``` sql
    create table stu (
        id int,
        phone_name varchar(20) unique -- 电话号不允许重复
    );
    ```
    - 注意，在 mysql 中，唯一约束限定的值可以有多个 null
    - 删除唯一约束
    ``` sql
    alter table stu drop index phone_number;
    ```
    - 创建表后添加唯一约束
    ``` sql
    alter table stu modify phone_number varchar(20) unique;
    ```
* 主键约束: primary key
    - 注意
        - 含义: 非空且唯一
        - 一张表只能有一个主键
        - 逐渐就是一张表中记录的唯一标识
    - 在创建表时添加主键约束
    ``` sql
    create table stu(
        id int primary key,
        name varchar(20)
    );
    ```
    - 删除主键
    ``` sql
    -- alter table stu modify id int; -- 错误
    alter table stu drop primary key;
    ```
    - 创建完表后添加主键
    ``` sql
    alter table stu modify id int primary;
    ```
    - 自动增长
        - 概念: 如果某一列是数值类型，使用 auto_increment 可以完成值的自动增长
        - 添加主键，并设置自动增长
            ``` sql
            create table stu(
                id int primary key auto_increment,
                name varchar(20)
            );
          
            alter table stu id int auto_increment;
            ```             
        - 删除自动增长
            ``` sql
            alter table stu modify id int;
            ```
* 外键约束: foreign key，让表与表产生关系，从而保证数据的正确性
    - 在创建表时添加外键
        ``` sql
        -- 语法
        create table xxx (
            ...
            外键列
            constraint 外键名称 foreign key 外键列名称 reference 主表名称(主表列名称)
        );
        ```
    - 删除外键
        ``` sql
        alter table stu drop foreign key 外键名
        ```
    - 在创建表之后添加外键
        ``` sql
        alter table my_table add constraint 外键名 foreign key (外键字段名称) reference 主表名称(主表列名称)
        ```
    - 添加外键，设置级联更新，设置级联删除
        ``` sql
        alter table my_table add constraint 外键名 foreign key (外键字段名称) reference 主表名称(主表列名称) on update cascade on delete cascade;
        ```
    - 级联操作
        - 添加级联
            ``` sql
            alter table my_table add constraint 外键名 foreign key (外键字段名称) reference 主表名称(主表列名称) on update cascade on delete cascade;
            ```
        - 分类
            ``` sql
            - 级联更新
                on update cascade;
            - 级联删除
                on delete cascade;
            ```

## 数据库设计
#### 多表之间的关系

* 分类
    - 一对一
    - 一对多
    - 多对多
* 实现关系
    - 一对多
        在多的一方建立外键，指向一的一方主键
    - 多对多
        多对多的关系实现需要借助第三张中间表，中间表至少包含两个字段，这两个字段作为第三张表的外键，分别指向两张表的主键
        
#### 数据库设计的范式，设计数据库时，需要遵循的一些规范，要遵循后边的范式要求，必须先遵循前面的所有范式要求
* 第一范式（1NF）
    - 每一列都是不可分割的原子数据项
* 第二范式（2NF）
    - 在1NF的基础上，非码属性必须完全依赖码（在1NF基础上消除非主属性对主码的部分函数依赖）
    - 函数依赖
        - A-->B 如果通过A的属性值可以确定唯一B属性的值，则称B依赖于A
        - 如: 学号-->姓名
    - 完全函数依赖
        - A-->B 如果A是一个属性组，则B属性值的确定需要依赖于A属性组中所有的属性值
        - 如: (学号,课程名称) --> 分数
    - 部分函数依赖
        - A-->B 如果A是一个属性组，则B属性值的确定只需依赖于A属性组中的某一些值即可。
        - 如: (学号,课程名称) --> 姓名
    - 传递函数依赖
        - A-->B,B-->C 如果通过A属性（组）的值，可以唯一确定B属性的值，在通过B属性（组）的值可以唯一确定C属性的值，则称C传递依赖于A
        - 如: 学号-->系名，系名-->系主任
    - 码
        - 如果在一张表中，一个属性或属性组，被其他所有属性所完全依赖，则称这个数学或属性组为该表的码
        - 如: 该表的码为: (学号,课程名称)
            - 主属性：码组属性中的所有属性
            - 非主属性：除过码属性组的属性
* 第三范式（3NF）
    - 在2NF的基础上，任何非主属性不依赖于其他非主属性（在2NF基础上消除传递依赖）

## 数据库的备份和还原
#### 命令行
* 语法 
    - 备份 mysqldump -u用户名 -p密码 数据库名 > 保存的路径
    - 还原 
        - 登录数据库
        - 创建数据库
        - 使用数据库
        - 执行文件。 source 文件路径

#### 图形化工具
* sqlyog
* navicat

## 多表查询

#### 内连接查询
* 隐式内连接：使用 where 条件消除无用数据
    例子
        select * from emp e,dept d where e.id = d.id;
* 显示内连接：
    - 语法
        select 字段列表 from 表名1 inner join 表名2 on 条件
        例子
            select * from emp [inner] join dept on emp.id = dept.id;
            -- inner 可省
            select * from emp join dept on emp.id = dept.id;
* 内连接查询
    - 从哪些表中查询
    - 条件是什么
    - 查询那些字段

#### 外连接查询
* 左外连接
    - 语法
        ``` sql
        select 字段列表 from 表1 left [outer] join 表2 on 条件
        ```
    - 查询的是左表所有数据以及交集部分（where部分匹配的即是交集部分）
* 右外连接
    - 语法
        ``` sql
        select 字段列表 from 表1 left [outer] join 表2 on 条件
        ```
    - 查询的是右表所有数据以及交集部分（where部分匹配的即是交集部分）

#### 子查询
* 概念，查询中嵌套查询，称嵌套查询为子查询
    ``` sql
    -- 如：查询工资最高的员工信息
    select * from emp where smp.salary = (select max(salary) from emp);
    ```
* 子查询的不同情况
    - 子查询的结果是单行单列的
        ``` sql
        -- 子查询可以作为条件，使用运算符去判断
        -- 如，查询员工工资小于平均工资的人    
        select * from emp where salary < (select avg(salary) from emp);
        ```
    - 子查询的结果是多行单列的
        ``` sql
        -- 子查询可以作为条件，使用运算符 in 来判断
        -- 如，查询 财务部 和 市场部 所有员工的信息
        select * from emp where dept_id in (select id from dept where name = '财务部' or '市场部');
        ```
    - 子查询的结果是多行多列的
        ``` sql
        -- 子查询可以作为一张虚拟表参与查询。
        -- 如，查询员工的入职日期是2011-11-11之后的员工信息和部门信息
        select * from dept t1, (select * from emp where emp.join_date > '2011-11-11') t2 where t1.id = t2.id;
        ```
     
## 事务
#### 事务的基本介绍
* 概念
    - 如果一个包含多个步骤的业务操作被事务管理，那么这些操作要么同时成功，要么同时失败
* 操作
    - 开启事务
        `start transaction`
    - 回滚
        `rollback`
    - 提交事务
        `commit`
    ``` sql
    -- 张三给李四转500块
    -- 0.开启事务
        start transaction 
    -- 1.张三 -500
        update account set balance = balcace - 500 where name = 'zhangsan';
    -- 2.李四 +500
        update account set balance = balance + 500 where name = 'lisi';
    -- 发现没有问题，提交事务
        commit;
    -- 发现有问题，回滚
        rollback;
    ```     
    - mysql 数据库中事务默认自动提交
        - 事务提交的两种方式
            - 自动提交，一条 DML(增删改)语句会自动提交一次事务，mysql 默认自动提交
            - 手动提交，需要先开启事务，Oracle 默认手动提交
        - 修改事务的默认提交方式
            - 查看事务的默认提交方式 select @@autocommit; -- 1 代表自动提交 0代表手动提交
            - 修改默认提交方式 set @@autocommit = 0; -- 修改为手动提交

#### 事务的四大特征
* 原子性
    - 是不可分割的最小操作单位，要么同时成功，要么同时失败
* 一致性
    - 事务操作前后，总量不变
* 隔离性
    - 多个事务之间相互独立
* 持久性
    - 当事务提交或回滚后，数据库会发生持久化的保存数据
    
#### 事务的隔离级别
* 概念
    - 多个事务之间是相互独立的。但是如果多个事务操作同一批数据，则会引发一些问题，设置不同隔离级别，可以解决问题
* 存在的问题
    - 脏读，一个事务，读取到另一个事务中没有提交的数据
    - 不可重复读（虚读），在同一个事务中，两次读取到的数据不一样
    - 幻读，一个事务操作（增删改）数据表中所有记录，另一个事物添加了一条数据，则第一个事物查询不到自己的修改
* 隔离级别
    - read uncommitted, 读未提交
        产生的问题，脏读，不可重复读，幻读
    - read commited, 读已提交 （Oracle 默认）
        产生的问题，不可重复读，幻读
    - repeatable read, 可重复读 （mysql默认）
        产生问题，幻读
    - serializable, 串行化
        可解决所有问题
    - 注意，隔离级别安全性越来越高，但效率越来越低
    - 数据库查询隔离级别
        select @@tx_isolation
    - 数据库设置隔离级别
        set global transaction isolation level 级别字符串