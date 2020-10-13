## Redis

#### 概念
* redis 是一款高性能的 NoSQL 系列的非关系型数据
#### 下载安装
* windows 下载地址：https://github.com/dmajkic/redis/downloads
* 下载解压直接使用
    - redis.windows.conf: 配置文件
    - redis-cli.exe: redis客户端
    - redis-server.exe: redis服务器端
#### 命令操作
* redis的数据结构
    - redis存储的是 key:value 格式的数据，key是字符串，值有5种数据结构
        - 字符串类型 string
        - 哈希类型 hash: map格式
        - 列表类型 list: linkedList格式
        - 集合类型 set: 不允许重复
        - 有序集合类型 sortedset
* 字符串类型 string
```
// 存储
set key value
// 获取
get key
// 删除
del key
```
* 哈希类型 hash
```
// 存储
hset key field value
// 获取
hget key field
// 获取所有值
hgetall key
// 删除
hdel key field
```
* 列表类型 list, 可以添加一个元素到列表的头部或尾部
```
// 存储 
lpush key value 将元素添加到列表的左边
rpush key value 将元素添加到列表的右边
// 获取
lrange key start end 范围获取
// 获取所有
lrange listA 0 -1
// 删除
lpop key 从列表左边弹出元素
rpop key 从列表右边弹出元素
```
* 集合类型 set, 不允许重复
```
// 存储
sadd key value
// 获取
smembers key // 获取set集合所有元素
// 删除
srem key value
```
* 有序集合类型 sortedset, 不允许重复，且元素有序
```
// 存储
zadd key score value // score 代表分数，即排序key
// 获取
zrange key start end
// 获取所有
zrange key 0 -1 // 不带分数的查询
zrange key 0 -1 withscore // 带分数的查询
// 删除
zrem key value
// 排序
```  
* 通用命令
```
keys * // 查询所有的键
type key // 获取键对应 value 的类型
del key // 删除指定的 key value
``` 
#### 持久化操作
* redis是一个内存数据化，当redis服务器重启数据就会丢失，可以将数据持久化存储到文件中
* redis持久化机制
    - RDB: 默认方式，不需要进行配置，默认使用此机制
        - 在一定的间隔时间中检测key的变化情况，然后持久化数据
        - 编辑conf文件
            ```
            # after 900 sec (15 min) if at least 1 key change
            save 900 1
            # after 300 sec (5 min) if at least 10 keys change
            save 300 10
            # after 69 sec (1 min) if at least 10000 keys change
            save 60 10000
            ```
        - 重新启动 redis 服务器，并指定配置文件  
        `redis-server.exe redis.windows.conf`
    - AOF: 日志记录的方式，记录每一条命令的操作，每一次命令操作
        - 编辑conf文件
        ```
        appendonly no (关闭AOF) 改为 appendonly yes (开启AOF)
        # appendfsyc always  // 每一次操作都进行持久化存储
        appendfsunc everysec // 每隔一秒进行一次持久化
        # appendfsync no     // 不进行持久化
        ```
    
#### 使用 Java 客户端操作 redis

* Jedis: 一款java操作redis数据库的工具
    - 导入jar包
        ```
        <!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>3.3.0</version>
        </dependency>
        
        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-pool2 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
            <version>2.9.0</version>
        </dependency>
        ```
    - 快速入门
        ```
        // 1.获取连接
        Jedis jedis = new Jedis("localhost", 6379);
        
        // 2.操作
        jedis.set("name","joush");
        
        // 3.关闭连接
        jedis.close();
        ```
    - Jedis操作各种redis中的数据结构
        ```
        // 1.字符串类型 string
        set
        get
        // 2.哈希类型 hash
        hset
        hget
        // 3.列表类型 list
        lpush / rpush
        lpop / rpop
        // 4.集合类型 set
        sadd
        srem
        // 5.有序集合类型 sortedset
        zadd
        zrem
        ```
    - Jedis 连接池，JedisPool
        - 创建连接池配置对象
        `JedisPoolConfig config = new JedisPoolConfig();`
        - 添加连接池配置参数
        `config.setMaxTotal(50); // 最大连接数`
        - 创建连接池对象
        `JedisPool jedisPool = new JedisPool(config, "localhost", 6379);`
        - 调用 getResource() 方法来获取连接
        `Jedis jedis = jedisPool.getResource();`
        - 连接池参数列表
            ```
            # 最大活动对象数
            redis.pool.maxTotal = 1000
            
            # 最大能够保持 idel 状态的对象数
            redis.pool.maxIdex = 100
            
            # 最小能够保持 idel 状态的对象数
            redis.pool.minIdex = 50
            
            # 当池内没有对象时，最大等待时间
            redis.pool.maxWaitMillis = 10000
            
            # 当调用 borrow Object 方法时，是否进行有效性检查
            redis.pool.testOnBorrow = true
            
            # 当调用 return Object 方法时，是否进行有效性检查
            redis.pool.testOnReturn = ture
            
            # "空闲连接"检测线程，检测周期，毫秒数。如果为负值，表示不运行"检测线程"。默认 -1
            redis.pool.timeBetweenEvictionRunsMillis = 30000
            
            # 向调用者输出"链接"对象时，是否检测空闲超时
            redis.pool.testWhileIdel = true
            
            # 对于"空闲链接"检测线程而言，每次检测的链接资源个数，默认为3
            redis.pool.numTestsPerEvictionRun = 50
            
            # redis服务器的 ip
            redis.pool.ip = x.x.x.x
            
            # redis服务器的 Port
            redis.pool.port = 6379
            ```
    