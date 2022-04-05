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

  