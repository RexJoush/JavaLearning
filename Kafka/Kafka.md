# Kafka 快速入门

## 安装部署

### 环境准备

#### 下载

* [Kafka 官网](https://kafka.apache.org/downloads)，[Zookeeper 官网](https://zookeeper.apache.org/releases.html)，[Hadoop 官网]()
* 下载到某个地方，并改名字
* 本项目地址为 `/opt/module/kafka` 和 `/opt/module/zookeeper`

#### 配置三台主机

| 主机名          | 安装组件                 |
| --------------- | ------------------------ |
| 192.168.184.131 | zookeeper，kafka，hadoop |
| 192.168.184.132 | zookeeper，kafka，hadoop |
| 192.168.184.133 | zookeeper，kafka，hadoop |

### 安装部署 zookeeper

#### 具体步骤

* 在安装 zookeeper 之前需安装 java 环境，此处就不介绍了，需先安装 java

* 解压第一步下载的文件 `apache-zookeeper-3.8.0-bin.tar.gz`，改名 `mv apache-zookeeper-3.8.0 zookeeper `

  ```shell
  # 1-3 步，三台主机均需要进行
  # 1.修改配置文件
  cd /opt/module/zookeeper/conf
  # 2.将 zoo_sample.cfg 复制一份改名为 zoo.cfg，zoo.cfg 为启动时的默认配置文件名
  cp zoo_sample.cfg zoo.cfg
  
  # 3.修改下面几处配置
  dataDir=/opt/module/zookeeper/data
  dataLogDir=/opt/module/zookeeper/logs
  # 在最后添加下面的映射，ip地址为三台集群的地址
  server.1=192.168.174.131:2881:32881
  server.2=192.168.174.133:2881:32881
  server.3=192.168.174.132:2881:32881
  
  # 4.添加 myid 文件
  # 在 server.1 对应的主机上，在 zookeeper 根目录新建两个文件夹 data 和 logs
  cd /opt/module/zookeeper
  mkdir data
  mkdir logs
  # 在 data 中新建文件 myid
  cd data
  vi myid
  # 在 myid 中写个 1 即可
  
  # 5.同理，在 server2,server3 中同样的操作，myid 修改为 2，3即可
  ```

#### 修改环境变量

```shell
# 添加环境变量文件，三台主机均需要进行
vi /etc/profile.d/my_env.sh

# 填写 zookeeper 的地址
# Zookeeper Home
export ZOOKEEPER_HOME=/opt/module/zookeeper
export PATH=$PATH:$ZOOKEEPER_HOME/bin
```

#### 放开端口

```shell
# 此处的两个端口为上面配置文件中写的两个端口，需要在三台主机上均放开
firewall-cmd --zone=public --add-port=2881/tcp --permanent
firewall-cmd --zone=public --add-port=32881/tcp --permanent
```

#### 尝试运行

```shell
cd /opt/module/zookeeper/bin
# 启动
./zkServer.sh start
# 查看状态，Mode 说明为 leader 或者 follower
[root@localhost bin]# ./zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /opt/module/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost. Client SSL: false.
Mode: leader

# 查看运行情况出现 QuorumPeerMain 表示运行成功
[root@localhost bin]# jps
6790 Jps
6591 QuorumPeerMain
```

### 安装 kafka

#### 具体步骤

* 将第一步下载的文件放在 `/opt/module` 目录下

  * kafka_2.12-3.1.0.tgz
  
* 修改 kafka 配置文件 `/opt/module/kafka/config/`
  * servcer.properties

    ```shell
    # kafka集群的唯一标识，三台主机需要不一样，一定注意，取 0，1，2 即可
    broker.id=0
    # 修改 kafka 日志文件的存储目录
    log.dirs=/opt/module/kafka/datas
    # 修改 zookeeper 的连接地址，默认是 localhost:2181
    zookeeper.connect=192.168.174.131:2181,192.168.174.132:2181,192.168.174.133:2181/kafka 
    ```

  * 配置环境变量，三台均需要进行

    ```shell
    # 1.修改上一步新建的环境变量文件
    vi /etc/profile.d/my_env.sh
    
    # 2.填写环境变量，配置 java，hadoop，kafka的环境变量
    
    # Zookeeper Home
    export ZOOKEEPER_HOME=/opt/module/zookeeper
    export PATH=$PATH:$ZOOKEEPER_HOME/bin
    
    # Java Home
    export JAVA_HOME=/opt/module/jdk-11.0.14
    export PATH=$PATH:$JAVA_HOME/bin
    
    # Hadoop Home
    export HADOOP_HOME=/opt/module/hadoop
    
    export PATH=$PATH:$HADOOP_HOME/bin
    export PATH=$PATH:$HADOOP_HOME/sbin
    
    # Kafka Home
    export KAFKA_HOME=/opt/module/kafka
    export PATH=$PATH:$KAFKA_HOME/bin
    
    # 3.生效
    source /etc/profile
    ```

  #### 尝试运行 kafka

  ```shell
  $ cd /opt/module/kafka
  $ bin/kafka-server-start.sh -daemon config/server.properties
  
  # 查看状态,运行成功，在三台主机均进行，出现下面即表示成功
  [root@localhost kafka] jps
  7344 QuorumPeerMain
  8865 Kafka
  8881 Jps
  ```

## Kafka 命令行操作

#### 主题（Topic）命令行操作

* /bin/kafka-topics.sh -argument

    | 参数                                              | 描述                               |
    | ------------------------------------------------- | ---------------------------------- |
    | --bootstrap-server <String: server toconnect to>  | 连接 Kafka Broker 主机名称和端口号 |
    | --topic <String: topic>                           | 操作的 topic 名称                  |
    | --create                                          | 创建主题                           |
    | --delete                                          | 删除主题                           |
    | --alter                                           | 修改主题                           |
    | --list                                            | 查看所有主题                       |
    | --describe                                        | 查看主题描述                       |
    | --pertitions <Integer: # of patitions>            | 设置分区数                         |
    | --replication-factor<Integer: replication factor> | 设置分区副本                       |
    | --config <String: name=value>                     | 更改系统默认设置                   |

* 查看系统的主题

    ```shell
    # 查看所有主题，默认端口是 9092
    bin/kafka-topics.sh --bootstrap-server 192.168.174.131:9092 --list
    ```

* 创建主题

  ```shell
  # 创建一个新的主题，first 为名字，指定分区为 1，创建 3 个副本数，三个参数均需要指定
  ./kafka-topics.sh --bootstrap-server 192.168.174.131:9092 --topic first --create --partitions 1 --replication-factor 3
  # 创建成功提示
  Created topic first.
  
  # 查看详情
  ./kafka-topics.sh --bootstrap-server 192.168.174.131:9092 --topic first --describe
  # 列出了详细信息
  # 主题 first，主题 id，分区数 1，副本数3，每个分区片的大小，1G
  Topic: first	TopicId: ddrH_SBsSDuyRUjc1qaoBQ	PartitionCount: 1	ReplicationFactor: 3	Configs: segment.bytes=1073741824
  # 主题 first，分区数为 0，因为上面设置了1个分区，从0开始，Leader 表示集群领导者的 broker_id 为 1号机，副本的所在机器
  Topic: first	Partition: 0	Leader: 1	Replicas: 1,0,2	Isr: 1,0,2
  ```

* 修改主题

  ```shell
  # 将分区修改为 3，此处需要注意，之前是分区1 ，分区修改只能增加，不能减少，即可以将 1 变为 3，不能反过来
  ./kafka-topics.sh --bootstrap-server 192.168.174.131:9092 --topic first --alter --partitions 3
  # 查看结果
  ./kafka-topics.sh --bootstrap-server 192.168.174.131:9092 --topic first --describe
  ```

#### 生产者（producer），消费者（consumer）命令行

* ./kafka-console-producer.sh ，./kafka-console-consumer.sh

* 生产数据

  ```shell
  # 向 first 中生产数据，出现 > 写入数据即可
  ./kafka-console-producer.sh --bootstrap-server 192.168.174.131:9092 --topic first
  > 
  ```

* 消费数据

  ```shell
  # 向 first 中生产数据，出现 > 写入数据即可
  ./kafka-console-consumer.sh --bootstrap-server 192.168.174.131:9092 --topic first
  > 
  ```

  

