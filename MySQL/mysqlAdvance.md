# MySQL 高级

## 1.MySQL 架构介绍

#### MySQL 配置文件

* 二进制日志 log-bin
  * 主从复制

* 错误日志 log-error
  * 默认关闭，记录严重的警告和错误信息，每次启动和关闭的详细信息等

* 查询日志 log
  * 默认关闭，记录查询的 sql 语句

* 数据文件
  * Linux  /var/lib/mysql
  * Windows  x:\\MySQLServer8.0\dara
  * frm 文件，存放表结构
  * myd 文件，存放表数据
  * myi 文件，存放表索引

* 如何配置
  * Linux  /etc/my.cnf
  * windows my.ini

#### MySQL 逻辑架构介绍

* 分层架构
  * 连接层，通过连接器和各种语言相连接（JDBC, ODBC, PHP, Perl）
  * 服务层
    * SQL interface，读取 SQL 语句
    * Parser，SQL 的语法分析，词法分析
    * Optimizer，SQL 优化器优化
    * Caches & Buffers，缓存或缓冲
  * 引擎层
    * InnoDB
    * MyISAM
    * NDB
    * ···
  * 存储层
    * 存储的实际位置，文件，logs 等

#### MySQL 存储引擎

* 查看命令
  * `show engines;`

* MyISAM 和 InnoDB 对比

  | 对比项   | MyISAM                                             | InnoDB                                                       |
  | -------- | -------------------------------------------------- | ------------------------------------------------------------ |
  | 主外键   | 不支持                                             | 支持                                                         |
  | 事务     | 不支持                                             | 支持                                                         |
  | 行表锁   | 表锁，即使操作一条记录也会锁住整个表，不适合高并发 | 行锁，操作时只锁一行，不对其他行有影响，适合高并发           |
  | 缓存     | 只缓存索引                                         | 不仅缓存索引，还缓存真实数据，对内存要求较高，内存大小对性能有决定影响 |
  | 表空间   | 小                                                 | 大                                                           |
  | 关注点   | 性能                                               | 事务                                                         |
  | 默认安装 | 是                                                 | 是                                                           |



## 2.索引优化分析

#### 性能下降 SQL 慢，执行时间长，等待时间长

* 查询语句写的烂

* 索引失效

  * 单值

    ```sql
    /*  表结构
    	id name email wxNumber
    */
    -- 1.查询语句
    select * from user where name = '';
    
    -- 2.建立索引
    -- 即，一般索引以 idx 开始，在 user 表的 name 字段上建立单值索引
    create index idx_user_name on user(name);
    ```

  * 复合

    ```sql
    -- 1.查询语句
    select * from user where name = '' and email  ='';
    
    -- 2.建立多值索引
    create index idx_user_nameEmail on user(name, email);
    ```

* 关联查询太多 jion (设计缺陷或不得已的需求)

* 服务器调优及各个参数设置（缓冲，线程数等）

#### 常见的 Join 查询

* SQL 执行顺序

  * 手写

    ```sql
    SELECT DISTINCT
    	select_list
    FROM
    	left_table join_type
    JOIN right_table ON join_condition
    WHERE
    	where_condition
    GROUP BY
    	group_by_list
    HAVING
    	having_condition
    ORDER BY
    	order_by_condition
    LIMIT limit_number
    ```

  * 机读

    ```sql
    FROM left_table
    ON join_condition
        join_type JOIN right_table
    WHERE where_condition
    GROUP BY group_by_list
    HAVING having_condition
    SELECT DISTINCT select_list
    ORDER BY order_by_condition
    LIMIT limit_number
    ```

* join 图

* 建表 SQL

* 7 种 join

  * inner join (内连接), A B 两张表的共有部分，即连接条件可以匹配上的值

    *  `select * from a inner join b on a.key = b.key; `

  * left join (左连接), A B 共有，加上 A 表的独有，即全 A，如果 B 表没有，则以空代替，即A 表的所有值均列出，某条记录无法匹配到 B 表的内容，则置空

    * `select * from a left join b on a.key = b.key;`

  * right join (右连接), A B 共有，加上 B 表独有，即全 B，如果 A 表没有，则以空代替，同左连接，以右表为基础

    *  `select * from a right join b on a.key = b.key;`

  * left join  (左连接)，A 中去除 B 中已有的

    * `select * from a left join b on a.key = b.key where b.key is null;`

  * right join  (右连接)，B 中去除 A 中已有的

    * `select * from a right join b on a.key = b.key where a.key is null;`

  * outer join  (全连接)，包含所有 A, B 的内容，但 mysql 不支持此语法

    * `select * from a full outer join b on a.key = b.key;`

    * mysql 语法，左连接获得左表全部，右连接获得右表全部，union 做连接即可，同时 union 会去重

    * ```sql
      select * from a left join b on a.key = b.key
      union
      select * from a right join b on a.key = b.key;
      ```

  * outer join  (全连接)，包含所有 A, B 的内容，并去除 A B 共有的部分

    * `select * from a full outer join b on a.key = b.key where a.key is null and b.key is null;`

    * mysql 语法，左连接获得左表独有，右连接获得右表独有，取 union 即可

    * ```sql
      select * from a left join b on a.key = b.key where b.key is null
      union
      select * from a right join b on a.key = b.key where a.key is null;
      ```

#### 索引简介

* 索引是什么

  * 官方对索引的定义为：索引是帮助 MySQL 高效获取数据的数据结构。
  * 索引的目的在于提高查找效率，类比字典
  * 排好序的快速查找数据结构，可叫索引
  * 在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据以某种方式指向数据，这样就可以在这些数据结构上实现高级的查找算法，这种数据结构，就是索引。
  * 一般来说索引本身也很大，不可能全部存在内存中，因此索引往往以索引文件的形式，存在磁盘上

* 优势

  * 提高检索效率，降低数据库的IO成本
  * 降低数据排序成本，降低 CPU 损耗

* 劣势

  * 实际索引也是表，该表保存了主键与索引字段，所以索引也是要占用空间的
  * 索然索引会大大提高查询速度，但会降低更新速度，更新数据时，也需要更新索引
  * 索引知识提高效率的一个因素，如果有大量的表，需要花时间研究建立最优秀的索引，或优化查询

* mysql 索引分类

  * 单值索引，即一个索引只包含单个列，一个表可以有多个单列索引

  * 唯一索引，索引列的值必须唯一，但允许有空值

  * 复合索引，即一个索引包含多个列

  * 基本语法

    ```sql
    -- 1.创建
    CREATE [UNIQUE] INDEX indexName ON mytable(col_name(length));
    ALTER mytable ADD [UNIQUE] INDEX indexName ON (col_name(length));
    
    -- 2.删除
    DROP INDEX indexName ON mytable;
    
    -- 3.查看
    SHOW INDEX FROM mytable;
    ```

* mysql 索引结构

  * BTree 索引
  * Hash 索引
  * Full-Text 索引
  * R-Tree 索引 

* 哪些情况需要创建索引

  * 主键会自动建立唯一索引
  * 频繁作为查询条件的字段应建立索引
  * 查询中与其他表关联的字段，外键关系建立索引
  * 在高并发的情况下，建议使用复合索引
  * 查询中排序的字段，排序字段若通过索引取访问将大大提高排序速度
  * 查询中统计或分组字段

* 哪些情况不需要创建索引

  * 表记录太少
  * 经常增删改的表不建索引
  * where 条件用不到的字段不创建索引
  * 数据重复且分布平均的表字段，因此应该只为最经常查询和最经常排序的数据列建立索引，注意，如果某个数据列包含许多重复内容，为它建立索引就没有太大的实际效果

#### 性能分析

###### Explain

* 使用 explain 关键字可以模拟优化器执行 SQL 查询语句，从而知道 MySQL 是如何处理你的 SQL 语句的，分析你的查询语句或是表结构的性能瓶颈

* 能干吗

  * 表的读取顺序
  * 数据读取操作的操作类型
  * 那些索引可以使用
  * 那些索引被实际使用
  * 表之间的引用
  * 每张表有多少行被优化器查询

* 怎么用

  * explain + sql 语句

  * 执行计划包含的信息

    | id   | select_type | table | type | possible_keys | key  | key_len | ref  | rows | Extra |
    | ---- | ----------- | ----- | ---- | ------------- | ---- | ------- | ---- | ---- | ----- |
    |      |             |       |      |               |      |         |      |      |       |

* **id**，决定表的加载顺序

  * select 查询的序列号，包含一组数字，表示查询中执行 select 子句或操作表的顺序
  * 三种情况
    * id 相同，表示从上到下的执行顺序
    * id 不同，如果是子查询，id 值会递增，id 值越大，优先级越高，越先被执行
    * id 相同不同，同时存在。先执行 id 大的，相同的顺序执行。

* **select_type**，查询类型，用于区别普通查询，联合查询，子查询，嵌套查询等

  * *SIMPLE*，简单的 select 查询，不包括子查询或者 UNION
  * *PRIMARY*，查询中若包含任何复杂的子部分，最外层查询则标记为 primary
  * *SUBQUERY*，在 select 或 where 中包含的子查询
  * *DERIVED*，衍生，在 from 列表中包含的子查询被标记为衍生表，mysql 会递归的执行这些子查询，把结果放在临时表里
  * *UNION*，若第二个 select 出现在 union 之后，则被标记为 UNION；若 UNION 包含在 FROM 子句的子查询中，外层 select 被标记为 DRIVED
  * *UNION RESULT*，从 UNION 表获取结果的 select

* **table**，显示本行数据是关于哪一张表的

* **type**，访问类型，最好到最差依次为 system > const > eq_ref > ref > range > index > ALL

  * *system*，表只有一行记录，这是 const 类型的特例，平时不会出现，这个也可以忽略不计
  * *const*，表示通过索引一次就找到了，const 通途比较 primary key 或者 unique 索引，因为只匹配一行数据，所以很快，如将主键置于 where 列表中，MySQL 就能将该查询转换为一个常量
  * *eq_ref*，唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或唯一索引扫描
  * *ref*，非唯一性索引扫描，返回匹配某个单独值的所有行，本质上也是一种索引访问，返回所有匹配某个单独值的行，但，它可能找到多个符合条件的行，所以它应该属于查找和扫描的混合体
  * *range*，只检索给定范围的行，使用一个索引来选择行。key 列显示使用了哪个索引，一般为 where 子句中出现了 between、<、>、in 等的查询，范围扫描一遍比全表扫描要好，因为只需扫描一部分即可
  * *index*，FULL index scan，index 与 all 区别为 index 类型只遍历索引树，通常比 all 快，因为索引文件通常比数据文件小，即虽然 index 和 all 都是读全表，但 index 是读取索引，而 all 是从硬盘中读取的
  * *ALL*，FULL table scan，遍历全表以找到匹配的行
  * 一般来说，要保证查询至少到 range 级别，最好能达到 ref

* **possible_keys**，显示可能应用在这张表中的索引，一个或多个，查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用

* **key**，实际使用到的索引，如果为 NULL，则没有使用索引，若查询中出现了覆盖索引，则该索引仅出现在 key 列表中

* **key_len**，表示索引中使用的字节数

  * 可通过该列计算查询中使用的索引长度。在不损失精确性的情况下，越短越好
  * key_len 显示的值为索引字段的最大可能长度，并非实际使用长度，即 key_len 是根据表定义计算而得，不是表内检索出的

* **ref**，显示索引的哪一列被使用了，如果可能的话，是一个常数。哪些列或常量被用于查找索引列上的值

* **rows**，根据表统计信息及索引选用情况，大致估算出找到对于记录所需要读取的行数

* **Extra**，包含不适合在其他列中显示但十分重要的信息

  * *Using filesort*，说明 MySQL 会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。MySQL中无法利用索引完成的排序成为文件排序

  * *Using temporary*，使用了临时表保存中间结果，MySQL在对查询结果排序时使用临时表。常见于排序 order by 和分组查询 group by

  * *Using index*，表示相应的 select 用到了覆盖索引，避免访问了表的数据行，效率很高。如果同时出现了 using where，表明索引用来执行索引键值的查找，如果没有出现，则表示用来读取数据而非执行查找动作

    ```
    覆盖索引：指的是 select 语句在执行查找时，所需的字段完全被索引覆盖，则无需去数据表中查询，因此速度更快。
    即，在 col1, col2, col3 上建立了复合索引，执行下面的 sql 语句则会出现覆盖索引
    select col1, col2, col3 from table1;
    即所需要的数据列在索引中全部存在，无需通过索引回表查询
    ```

  * *Using where*，表明使用了 where 过滤

  * *Using join buffer*，表明使用了连接缓存

  * *impossible where*，where 子句的值总是 false，不能用来获取任何数据，如 where col = 'a' && col = 'b'

  * *select tables optimized away*，在没有 group by 子句的情况下，基于索引优化 max / min 操作，或者对于 MyISAM 引擎优化 count 操作，不必等到执行阶段在进行计算，查询执行计划生成阶段即完成优化

  * *distinct*，优化 distinct 操作，在找到第一个匹配的元组后即停止找同样值的动作

#### 索引优化

* 案例

    ```sql
    CREATE TABLE IF NOT EXISTS `article`(
        `id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
        `author_id` INT (10) UNSIGNED NOT NULL,
        `category_id` INT(10) UNSIGNED NOT NULL , 
        `views` INT(10) UNSIGNED NOT NULL , 
        `comments` INT(10) UNSIGNED NOT NULL,
        `title` VARBINARY(255) NOT NULL,
        `content` TEXT NOT NULL
    );
    
    -- 1.查询 category_id 为 1 且 comments 大于 1 的情况下，views 最多的 article_id
    select id,author_id from article where category_id = 1 and comments > 1 order by views desc limit 1;
    -- 出现了全表扫描，type 为 all，同时 extra 出现了 Using filesort，性能很低
    
    -- 2.在，category_id, comments, views 上创建复合索引
    create index idx_article_category_comments_views on article(category_id, comments, views);
    -- 此索引解决了全表扫描问题，但范围查找导致索引失效，依然存在 filesort 问题
    
    -- 3.删除不合适的索引，建立新的索引
    drop index idx_article_category_comments_views on article;
    
    -- 4.考虑绕开 comments字段，即建立 category_id views 索引
    create index idx_article_category_views on article(category_id, views);
    
    -- 5.此时即可解决，type 变为了 ref，同时 filesort也已经消失
    ```

* 建议
  * 对于单值索引，尽量选择针对当前 query 过滤性更好的索引
  * 在选择组合索引的时候，当前 query 中过滤性最好的字段在索引字段的顺序中，位置越靠前越好
  * 在选择组合索引的时候，尽量选择可以能够包含当前 query 中的 where 子句更多的字段
  * 尽可能通过分析统计信息和调整 query 的写法来达到选择合适索引的目的

* 索引是否被引用总结

* 假设创建索引 index(a, b, c)

  | Where 语句                                        | 索引是否被引用                                    |
  | ------------------------------------------------- | ------------------------------------------------- |
  | where a = 3                                       | Y，使用到 a                                       |
  | where a = 3 and b = 5                             | Y，使用到 a，b                                    |
  | where a = 3 and b = 5 and c = 4                   | Y，使用到 a，b，c                                 |
  | where b = 3 或者 where b = 3 and c = 4 或者 c = 4 | N                                                 |
  | where a = 3 and c = 5                             | Y，使用到 a，但 c 不可以                          |
  | where a = 3 and b > 4 and c = 5                   | Y，使用到 a 和 b，但 c 不可以                     |
  | where a = 3 and b like 'kk%' and c = 4            | Y，使用到 a，b，c                                 |
  | where a = 3 and b like '%kk' and c = 4            | Y，只用到 a                                       |
  | where a = 3 and b like '%kk%' and c = 4           | Y，只用到 a                                       |
  | where a = 3 and b like 'k%kk%' and c = 4          | Y，用到 a，b，c，即 like 后常量开头，即可用到所有 |

## 3.查询截取分析

#### 查询优化

* 永远小表驱动大表，类似嵌套循环 nested loop，即小的数据集驱动大的数据集

* order by 优化

  * order by 子句，尽量使用 index 方式排序，避免 filesort 方式
  * 尽可能在索引列上完成排序，遵照索引建的最佳前缀
  * 如果不在索引列上，filesort 有两种排序算法，单路排序和双路排序
    * 双路排序
    * 单路排序
    * 参数设置
      * order by 时，不要使用 select *
      * 尝试提高 sort_buffer_size 的容量
      * 尝试提高 max_length_for_sortt_data 的大小
  * 优化策略

* order by 可以使用索引的情况总结

  ```sql
  key a_b_c (a, b, c);
  
  -- 可以使用索引的最左前缀
  order by a;
  order by a, b;
  order by a, b, c;
  order by a DESC, b DESC, c DESC;
  
  -- 如果 往ere使用索引的最左前缀定义为常量，则 order by 可以使用索引
  where a = const order by b, c
  where a = const and b = const order by c
  where a = const and b > const order by b, c
  
  -- 不能使用索引进行排序
  order by a ASC, b DESC, c DESC; /* 排序不一致 */
  where g = const order by b, c;  /* 丢失 a 索引 */
  where a = const order by c ; 	/* 丢失 b 索引 */
  where a = const order by a, d;  /* 没有 d 索引 */
  where a in (...) order by a, d; /* 对于排序来说，多个相等的条件也是范围查询 */
  ```

* group by 优化
  * group by 的实质时先排序后分组，遵照索引建的最佳左前缀原则
  * 当前无法使用索引列，增大两个排序参数的值
  * where 高于 having，能写在 where 的条件就不要写在 having

#### 慢查询日志

* 是什么
  * MySQL 的慢查询日志是 MySQL 提供的一种日志记录，它用来记录在 MySQL 中响应时间超过阈值的语句，具体指运行时间唱过 long_query_time 的 SQL，则会被记录到慢查询日志中
  * long_query_time 的默认值是 10，即运行 10 秒以上的语句
  * 由它来查看哪些 SQL 超过了最大忍耐值
* 怎么用
  * 默认情况下没有开启慢查询，若不是调优需要，则不建议开启此功能，（8.0以后默认开启此功能）`set slow_query_log=1;`

#### Show Profile

* 是什么

  * MySQL 提供的可以用来分析当前会话中语句执行的资源消耗情况，可以用于 SQL 调优的测量
  * 默认情况下，参数处于关闭状态，并保存最近15 此的运行结果

* 分析步骤

  * 默认关闭，`show variables like 'profiling';` 开启 `set profiling=on;`

  * 查看 

    ```sql
    -- 列出最近的 sql 记录
    show profiles;
    -- 此处的 3 指的是上一步得到的第三条查询记录，
    -- 仅展示 cpu 和 io，也可加上 memory 内存，或者 all 查询所有
    show profile cpu, block id for query 3;
    -- 结果如下
    /*
    +--------------------------------+----------+----------+------------+--------------+---------------+
    | Status                         | Duration | CPU_user | CPU_system | Block_ops_in | Block_ops_out |
    +--------------------------------+----------+----------+------------+--------------+---------------+
    | starting                       | 0.000120 | 0.000000 |   0.000000 |         NULL |          NULL |
    | Executing hook on transaction  | 0.000002 | 0.000000 |   0.000000 |         NULL |          NULL |
    | starting                       | 0.000006 | 0.000000 |   0.000000 |         NULL |          NULL |
    | checking permissions           | 0.000010 | 0.000000 |   0.000000 |         NULL |          NULL |
    | Opening tables                 | 0.000034 | 0.000000 |   0.000000 |         NULL |          NULL |
    | init                           | 0.000004 | 0.000000 |   0.000000 |         NULL |          NULL |
    | System lock                    | 0.000017 | 0.000000 |   0.000000 |         NULL |          NULL |
    | optimizing                     | 0.000003 | 0.000000 |   0.000000 |         NULL |          NULL |
    | statistics                     | 0.000014 | 0.000000 |   0.000000 |         NULL |          NULL |
    | preparing                      | 0.000013 | 0.000000 |   0.000000 |         NULL |          NULL |
    | executing                      | 0.000041 | 0.000000 |   0.000000 |         NULL |          NULL |
    | end                            | 0.000002 | 0.000000 |   0.000000 |         NULL |          NULL |
    | query end                      | 0.000002 | 0.000000 |   0.000000 |         NULL |          NULL |
    | waiting for handler commit     | 0.000006 | 0.000000 |   0.000000 |         NULL |          NULL |
    | closing tables                 | 0.000005 | 0.000000 |   0.000000 |         NULL |          NULL |
    | freeing items                  | 0.000044 | 0.000000 |   0.000000 |         NULL |          NULL |
    | cleaning up                    | 0.000010 | 0.000000 |   0.000000 |         NULL |          NULL |
    +--------------------------------+----------+----------+------------+--------------+---------------+
    */
    ```

  * 需要注意，如果 Status 出现了下面几个就比较危险

    * converting HEAP to MyISAM 查询结果太大，内存不够用，移动到磁盘
    * Creating tmp table 创建临时表
    * copying to tmp table on disk 把内存中临时表复制到磁盘
    * locked

#### 全局查询日志

* 开启查询日志

  ```sql
  -- 1.开启
  set global general_log=1;
  set golbal log_output='TABLE';
  -- 2.此后，所有的 sql 会记录在 mysql 库 的 general_log 表中，可以用下面命令查看
  select * from mysql.general_log;
  ```

* 永远不要再生产环境开启此功能

## 4.MySQL 锁机制

#### 概述

* 定义
  * 锁是计算机协调多个进程或线程并发访问某一资源的机制。
* 锁的分类
  * 对数据操作的类型分
    * 读锁，也叫共享锁，针对同一份数据，多个读操作可以同时进行而不会相互影响
    * 写锁，排他锁，当前操作没有完成前，会阻断其他写锁和读锁。
  * 对数据操作粒度分，表锁，行锁

#### 表锁（偏度）

* 特点，偏向 NyIASM 引擎，开销小，加锁快；锁定粒度大，发生锁冲突的概率最高，并发度最低。

  ```sql
  -- 1.锁定某个表
  lock table tbl_name read[write], tbl_name2 read[write] ...
  
  -- 2.查看某张表是否被锁
  show open tables;
  
  -- 3.取消锁
  unlock tables;
  ```

* 表锁的可读可写总结

  * session 1 给 table1 加了**读锁**

      | session1         | session2             |
      | ---------------- | -------------------- |
      | 可以读取 table1  | 可以读取 table1      |
      | 不能对 table1 写 | 对 table1 写会被阻塞 |
      | 不能读 table2    | 可以读 table2        |

  * session 1 给 table1 加了**写锁**

      | session1         | session2                 |
      | ---------------- | ------------------------ |
      | 可以读取 table1  | 对 table1 读操作会被阻塞 |
      | 可以对 table1 写 | 对 table1 写会被阻塞     |
      | 不能读 table2    | 可以读 table2            |
  
  * 简而言之，读锁会阻塞写，但不会阻塞读，写锁会阻塞读写。

#### 行锁（偏写）

* 特点，偏向 InnoDB 存储引擎，开销大，加锁慢；会出现死锁；锁定力度小，发生锁冲突的概率最低，并发度也最高
* InnoDB 与 MyISAM 的最大不同有两点，一是支持事务（TRANSACTION），二是采用了行级锁
* 由于行锁支持事务，复习老知识
  * 事务及其 ACID 属性
    * **原子性（Atomicity）**，事务时最小的执行单位，不允许分割，事务的原子性确保动作要么全部完成，要么全部不做。
    * **一致性（Consistency）**，执行事务前后，数据保持一致，多个事务对同一个数据读取的结果时相同的
    * **隔离性（Isolation）**，并发访问数据库时，一个用户的事务不被其他事务所干扰，并发事务之间数据库时独立的
    * **持续性（Durability）**，事务提交后，它对于数据的修改是永久性的，即使出现系统故障也不应该有任何影响
    
  * 并发带来的问题
    * **脏读（Dirty read）**，事务 1 对某条数据进行修改，但事务还未提交，事务 2 对该数据进行了读取，但该数据有可能不会提交，因此事务2，即读取到了脏数据
    * **丢失修改（Lost to modify）**，事务 1 对某条数据进行修改，同时事务 2 对该数据也进行修改，则 事务 2 丢失了对数据的修改
    * **不可重复读（Unrepeatableread）**，事务 1 多次读取同一个数据，但在两次读取的过程中，事务 2 对该数据进行了修改，导致两次读取的数据不一样
    * **幻读（Phantom read）**，与不可重复读类似，发生在事务 1 读取几行数据，接着事务 2 插入了一些数据，在随后的查询时，事务 1 会发现多了一些原本不存在的数据，即好像发生了幻觉一样，称为幻读
    
  * 事务的隔离级别

    * **读未提交（Read uncommitted）**，最低的隔离级别，允许读取尚未提交的数据，可能导致脏读，幻读，或不可重复读

    * **读已提交（Read committed）**，允许读取并发事务已经提交的数据，可以阻止脏读，但依然会发生幻读和不可重复读

    * **可重复读（Repeatable read）**，对同一数据的多次读取结果都是一致的，除非数据是被本身事务所更改，可以阻止脏读和不可重复读，但依然会发生幻读

    * **串行化（Serializable）**，最高的隔离级别，完全服从 ACID 特性，所有事务逐个执行，此级别可以防止脏读，不可重复读以及幻读。

    * 查看事务的隔离级别 

      ```sql
      -- mysql 8.0+ 
      select @@transaction_isolation;
      -- mysql 5.7-
      show variables like 'tx_isolation';
      ```

* 案例分析

  * 无索引会导致行锁升级为表锁
  * 数据类型转换会导致索引失效，即字符串 4000，写成了数字 4000

* 间隙锁的危害

  * 当我们用范围条件而不是相等条件检索数据，并请求共享或排他锁时，InnoDB 会给符合条件的已有数据记录的索引项加锁，对于键值在范围之内但并不存在的记录，叫做间隙（GAP）。InnoDB 也会对这个 **间隙** 加锁，这种锁机制叫做间隙锁

    ```sql
    -- 数据表中的数据 table1
    id  name
    1   joush
    3   rex
    4   bonny
    5   ice
    
    -- 事务 1 执行操作
    update table1 set name = 'aaa' where id > 1 and id < 4;
    
    -- 事务 2 执行操作
    insert into table1 (id, name) values (2, 'aaa');
    /*
    即事务一对范围 1-4 之间的 id 均加了锁，因此事务 2 想要插入 id 为 2 的记录时，依然被阻塞
    */
    ```

  * 因为 Query 执行的过程中通过范围查找的话，会锁定整个范围内的所有索引键值，即使键值并不存在。因此在某些情况下会对性能造成很大的危害

* 如何锁定一行

  ```sql
  -- 1.开启事务
  begin;
  -- 2.开启行锁
  select * from table1 where a = '8' for update; -- 直到锁定的某一行 commit 之后，其他的事务才不会被阻塞
  -- 3.提交事务
  commit;
  ```

* 优化建议
  * 尽可能让所有数据检索都通过索引来完成，避免无索引行锁升级为表锁
  * 合理设计索引，尽量缩小锁的范围
  * 尽可能减少检索的条件，避免间隙锁
  * 尽量控制事务的大小，减少锁定资源量和时间长度
  * 尽可能低级别事务隔离

#### 页锁

* 开销和加锁时间介于表锁和行锁之间，会出现死锁，锁定粒度介于表锁和行锁之间，并发度一般，了解即可。

## 5.主从复制

#### 复制的基本原理

* slave 会从 master 读取 binlog 来进行数据同步
* MySQL 复制过程分三步
  * master 将改变记录到二进制日志（binary log）。这些记录过程叫做二进制日志事件，binary log events；
  * slave 将 master 的 binary log events 拷贝到它的中继日志（relay log）；
  * slave 重做中继日志中的事件，将改变应用到自己的数据库中。MySQL 复制时异步进行的且串行化的

#### 复制的基本原则

* 每个 slave 只有一个 master
* 每个 slave 只能有一个唯一的服务器 id
* 每个 master 可以有多个 slave

#### 复制的最大问题

* 存在一定的延时

#### 一主一从常见配置

