## 第 1 章 Zookeeper 入门

#### 1.1 概述

* Zookeeper 是一个开源的分布式的，为分布式框架提供协调服务的Apache 项目。

#### 1.2 Zookeeper工作机制

* zookeeper从设计模式角度来理解：是一个基于观察者模式设计的分布式服务管理框架，它负责
  存储和管理大家都关心的数据，然后接受观察者的注册，一旦这些数据的状态发生变化，Zookeeper
  就将负责通知已经在Zookeeper上注册的那些观察者做出相应的反应
* Zookeeper = 文件系统 + 通知机制
  * zookeeper 存储了各个服务的上线信息

#### 1.3 Zookeeper 特点

* zookeeper：一个领导者（Leader），多个跟随着（Follower）组成的集群
* 集群中只要有**半数以上**节点存活，zookeeper 集群就可以正常服务，所以 zookeeper 适合安装奇数台服务器
* 全局数据一致：每个 Server 保存一份相同的数据副本，Client 无论连接到哪个 Server，数据都是一致的
* 更新请求顺序执行，来自同一个 Client 的更新请求按其发送顺序依次执行
* 数据更新原子性，依次数据更新要么成功要么失败
* 实时性，在一定时间内，Client 能读到最新数据

#### 1.4 数据结构

* ZooKeeper 数据模型的结构与Unix 文件系统很类似，整体上可以看作是一棵树，每个节点称做一个 ZNode。

* 每一个 ZNode 默认能够存储1MB 的数据，每个 ZNode 都可以通过其路径来**唯一标识**

  ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/Zookeeper/filestruct.jpg?raw=true)

#### 1.5 应用场景

* 提供的服务包括：统一命名服务、统一配置管理、统一集群管理、服务器节点动态上下线、软负载均衡等。
* 统一命名服务
  * 在分布式环境下，经常需要对应用服务进行统一命名，便于识别。例如：IP不容易记住，而域名容易记住。

* 统一配置管理
  * 分布式环境下，配置文件同步非常常见
    * 一般要求一个集群中，所有节点的配置信息是一致的，比如Kafka 集群。
    * 对配置文件修改后，希望能够快速同步到各个节点上。
  * 配置管理可交由ZooKeeper实现
    * 可将配置信息写入ZooKeeper上的一个Znode。
    * 各个客户端服务器监听这个Znode。
    * 一旦Znode中的数据被修改，ZooKeeper将通知各个客户端服务器。

* 统一集群管理
  * 分布式环境中，实时掌握每个节点的状态是必要的，可根据节点实时状态做出一些调整
  * ZooKeeper可以实现实时监控节点状态变化
    * 可将节点信息写入ZooKeeper上的一个ZNode
    * 监听这个ZNode可获取它的实时状态变化
* 服务器动态上下线
  * 客户端能实时洞察到服务器上下线的变化
  * 1.服务端启动时去注册信息（创建都是临时节点）
  * 2.获取到当前在线服务器列表，并且注册监听
  * 3.服务器节点下线
  * 4.服务器节点上下线事件通知
* 软负载均衡
  * 在Zookeeper中记录每台服务器的访问数，让访问数最少的服务器去处理最新的客户端请求

## 第 2 章 Zookeeper本地安装

#### 2.1 本地模式安装

* [下载地址](https://zookeeper.apache.org/releases.html)

* 安装 zookeeper，只能在 linux 下安装

  * 1.安装 jdk 并配置环境变量

    ```shell
    # 1.解压下载的 zookeeper 压缩包
    tar zxvf apache zookeeper_3.8.0bin.tar.gz C /opt/module/
    
    # 2。将 /opt/module/zookeeper-3.5.7/con f这个 路径下的 zoo_sample.cfg 修改 为 zoo.cfg
    mv zoo_sample.cfg zoo.cfg
    
    # 3.修改配置文件
    vim zoo.cfg
    # 修改下面的位置
    dataDir=/opt/module/zookeeper/zkData
    
    # 4.在 /opt/module/zookeeper/这个 目录上创建 zkData文件夹
    
    ```

* 启动 zookeeper

  ```shell
  # 启动服务端
  cd /opt/module/zookeeper/bin
  zkServer.sh start
  
  # 查看进程
  jps
  4020 Jps
  4001 QuorumPeerMain # 表示成功情动
  
  # 启动客户端
  zkCli.sh
  
  # 退出客户端
  quit
  
  # 停止 zookeeper
  bin/zkServer.sh stop
  ```

#### 2.2 配置参数 解读

* Zookeeper中的配置文件 zoo.cfg 中参数含义解读如下
  * tickTime = 2000：通信心跳时间，Zookeeper服务器与客户端心跳时间，单位毫秒
  * initLimit = 10：LF 初始通信时限，Leader和Follower初始连接时能容忍的最多心跳数（tickTime的数量）
  * syncLimit = 5：LF同步通信时限，Leader和Follower之间通信时间如果超过syncLimit * tickTime，Leader认为Follwer死掉，从服务器列表中删除Follwer。
  * dataDir：保存Zookeeper中的数据
  * clientPort = 2181：客户端连接端口，通常不做修改。

## 第 3 章 Zookeeper集群操作

#### 3.1 集群操作

* 集群安装

  * 在另外两台机器上重复上一章的安装操作

* 配置服务器编号

  * 在 /opt/module/zookeeper/这个目录下创建 zkData

  * 在 zkData 目录中添加文件 myid

    ```shell
    vi myid
    # 填写一个唯一的 id，数字 1
    
    ```

  * 三台都需如此做，但 id 值不能一样，修改 id 值即可

* 配置 zoo.cfg 文件，三台机器均需要如此做

  ```shell
  # 修改 zoo.cfg 文件
  vim zoo.cfg
  
  # 添加如下内容
  server.1=hadoop102:2888:3888
  server.2=hadoop103:2888:3888
  server.3=hadoop104:2888:3888
  ```

  * 配置解读

    ```text
    server.A=B:C:D
    A 是一个数字，表示这个是第几号服务器；集群模式下配置一个文件 myid 这个文件在 dataDir目录 下，这个文件里面有一个数据就是 A 的值， Zookeeper启动时读取此文件，拿到里面的 数据与 zoo.cfg里面 的配置信息比
    较从而判断到底是哪个 server。
    
    B是这个服务器的地址；
    C是这个服务器 Follower与集群中的 Leader服务器交换信息的端口；
    D是 万一集群中的 Leader服务器挂了，需要一个端口来重新进行选举，选出一个新的
    Leader，而这个端口就是用来执行选举时服务器相互通信的端口。
    ```

#### 3.2 客户端命令行操作

* 命令行语法

  ```shell
  help      显示所有操作命令                                            
  ls path   使用 ls 命令来查看当前znode的子节点
  		  -w 监听子节点变化
  		  -s 附加次级信息
  create    普通创建
            -s 含有序列
            -e 临时（重启或超时消失）  
  get path  获得结点的值
            -w 监听节点内容变化
            -s 附加次级信息
  set       设置节点的具体值                                            
  stat      查看节点状态                                                
  delete    删除节点                                                    
  deleteall 递归删除节点                                                
  ```

* znode节点数据信息

  * 查看当前znode中所包含的内容 `ls /`

  * 查看当前节点详细数据 `ls -s /`

    ```shell
    $ ls s /
    [zookeeper]
    cZxid = 0x0  # 创建节点的事务 zxid 每次修改 ZooKeeper 状态都会产生一个 ZooKeeper 事务 ID。事务 ID 是 ZooKeeper 中所有修改总的次序。每次修改都有唯一的 zxid，如果 zxid1 小于 zxid2，那么 zxid1在 zxid2 之前发生。
    ctime = Thu Jan 01 08:00:00 CST 1970 # znode被创建的毫秒数（从 1970年开始）
    mZxid = 0x0  # znode最后更新的事务 zxid
    mtime = Thu Jan 01 08:00:00 CST 1970 # znode最后修改的毫秒数（从 1970年开始）
    pZxid = 0x0 # znode最后更新的子节点 zxid
    cversion = 1 # znode 子节点变化号，znode 子节点修改次数
    dataVersion = 0 # znode 数据变化号
    aclVersion = 0 # znode 访问控制列表的变化号
    ephemeralOwner = 0x0 # 如果是临时节点，这个是znode 拥有者的session id。如果不是临时节点则是0。
    dataLength = 0 # znode 的数据长度
    numChildren = 1 # znode 子节点数量
    ```

* 节点类型

  * 持久（Persistent）：客户端和服务器端断开连接后，创建的节点不删除
  * 短暂（Ephemeral）：客户端和服务器端断开连接后，创建的节点自己删除
  * （1）持久化目录节点
    * 客户端与 Zookeeper 断开连接后，该节点依旧存在
  * （2）持久化顺序编号目录节点
    * 客户端与 Zookeeper 断开连接后，该节点依旧存在，只是 Zookeeper 给该节点名称进行顺序编号
  * （3）临时目录节点
    * 客户端与 Zookeeper 断开连接后，该节点被删除
  * （4）临时顺序编号目录节点
    * 客户端与 Zookeeper 断开连接后， 该节点被删除， 只是 Zookeeper 给该节点名称进行顺序编号
  * 说明，创建 znode 时设置顺序标识，znode 名称后会附加一个值，顺序号是一个单调递增的计数器，由父节点维护

* 分别创建2个普通节点（永久节点 + 不带序号）

  ```shell
  # 1.创建目录节点，不带序号
  $ ls /
  [kafka, zookeeper]
  $ create /sanguo "diaochan"
  Created /sanguo
  $ ls /
  [kafka, sanguo, zookeeper]
  
  # 多目录创建
  $ create /sanguo/shuguo "liubei"
  Created /sanguo/shuguo
  $ ls /sanguo 
  [shuguo]
  
  # 2.获取创建节点的值
  $ get -s /sanguo
  diaochan
  cZxid = 0x600000005
  ctime = Sat Apr 30 19:37:36 CST 2022
  mZxid = 0x600000005
  mtime = Sat Apr 30 19:37:36 CST 2022
  pZxid = 0x600000006
  cversion = 1
  dataVersion = 0
  aclVersion = 0
  ephemeralOwner = 0x0
  dataLength = 8
  numChildren = 1
  ```

* 创建带序号的节点（永久节点 + 带序号）

  ```shell
  # 创建魏国
  $ create /sanguo/weiguo "caocao"
  Created /sanguo/weiguo
  $ ls /sanguo
  [shuguo, weiguo]
  
  # 创建带序号的节点 张辽
  $ create -s /sanguo/weiguo/zhangliao "zhangliao"
  Created /sanguo/weiguo/zhangliao0000000000
  $ ls /sanguo/weiguo
  [zhangliao0000000000]
  
  # 再次创建曹操和张辽，发现，曹操提示已经错存在，带序号的张辽则自增 1 成功创建
  $ create /sanguo/weiguo "caocao"
  Node already exists: /sanguo/weiguo
  $ create -s /sanguo/weiguo/zhangliao "zhangliao"
  Created /sanguo/weiguo/zhangliao0000000001
  ```

* 创建短暂节点（短暂节点 + 不带序号 or 带序号）

  ```shell
  # 创建临时节点 参数为 -e
  $ create -e /sanguo/wuguo "zhouyu"
  Created /sanguo/wuguo
  $ create -e -s /sanguo/wuguo "zhouyu"
  Created /sanguo/wuguo0000000003
  $ ls /sanguo
  [shuguo, weiguo, wuguo, wuguo0000000003] # 可以看到，此处序号从3 开始了
  
  $ quit # 退出
  
  $ ls /sanguo # 再次查看，发现只有吴国消失了
  [shuguo, weiguo]
  ```

* 修改节点的值

  ```shell
  # 修改值
  $ set /sanguo/weiguo "simayi" # 将曹操改为司马懿 
  ```

* 监听器原理

  * 客户端注册监听它关心的目录节点，当目录节点发生变化（数据改变、节点删除、子目录节点增加删除）时，ZooKeeper 会通知客户端。监听机制保证ZooKeeper 保存的任何的数据的任何改变都能快速的响应到监听了该节点的应用程序

    * 节点的值变化监听

        ```shell
        # 在 2 号机上注册监听器，
        $ get -w /sanguo
        xishi
        # 此处时修改会才出现的
        WATCHER::
        
        WatchedEvent state:SyncConnected type:NodeDataChanged path:/sanguo
        # 在 3 号机上修改 sanguo 的值
        set /sanguo "xishi"
        set /sanguo "sunshangxiang" # 此处再次修改，则不会触发监听，因为值注册了一次，就只监听一次
        ```

    * 节点的子节点变化监听（路径变化），即添加或删除数量
    
      ```shell
      # 注册监听器
      $ ls w sanguo
      ```

* 节点的删除

  ```shell
  # 删除某个节点
  $ delete /sanguo/jin
  
  # 删除 sanguo
  $ delete /sanguo
  Node not empty: /sanguo # 发现提示非空，不能删除
  
  # 需要递归删除
  $ deleteall /sanguo
  ```

#### 3.3 IDEA 环境搭建

* 新建 maven 工程

* 添加依赖

  ```xml
  <dependencies>
      <dependency>
          <groupId>org.apache.zookeeper</groupId>
          <artifactId>zookeeper</artifactId>
          <version>3.8.0</version>
      </dependency>
      <dependency>
          <groupId>log4j</groupId>
          <artifactId>log4j</artifactId>
          <version>1.2.17</version>
      </dependency>
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.13.2</version>
      </dependency>
  </dependencies>
  ```

* 创建 log4j.properties

  ```properties
  log4j.rootLogger=INFO, stdout
  log4j.appender.stdout=org.apache.log4j.ConsoleAppender
  log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
  log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n
  log4j.appender.logfile=org.apache.log4j.FileAppender
  log4j.appender.logfile.File=target/spring.log
  log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
  log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
  ```

  
