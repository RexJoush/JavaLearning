## Docker

#### 安装 Docker
* 官方建议是用 Ubuntu，Cent OS 建议7以上版本
* 安装步骤
``` editorconfig
# yum 包更新到最新  
$ sudo yum update

# 安装需要的软件包，yum-util 提供 yum-config-manger 功能，另外两个是 device mapper 启动依赖的  
$ sudo yum install yum-utils device-mapper-persistent-data lvm2

# 设置 yum 源为阿里云(可选)  
$ sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 安装 Docker 社区版  
$ sudo yum install docker-ce

# 安装后查看 docker 版本  
$ docker -v
```

#### 设置国内的镜像
* 编辑文件  
`$ vi /etc/docker/daemon.json`
* 加入内容
    ``` json
    {
        "register-mirrors": ["https://docker.mirrors.ustc.edu.cn"]
    }
    ```

#### Docker 的启动与停止
``` editorconfig
# 启动  
$ systemctl start docker
# 查看服务  
$ systmectl status docker
# 停止  
$ systemctl stop docker
# 查看信息  
$ docker info
# 帮助文档  
$ docker --help
```

## Docker 常用命令

#### 镜像相关命令
``` editorconfig
# 查看安装的镜像
$ docker images 
# REPOSITORY  TAG     IMAGE_ID  CREATED   SIZE
# 镜像名称     版本     镜像id    创建时间   大小

# 搜索镜像
$ docker search xxx
# NAME    DESCRIPTION     STARS   OFFICAL     AUTOMATED
# 镜像名称    描述          星数    是否官方     是否由 Docker Hub自动构建流程创建的

# 拉取镜像  
$ docker pull xxx

# 删除镜像  
$ docker rmi xxx # 镜像名称或id

# 删除所有镜像
$ docker rmi `docker images -q` 
```

#### 容器相关命令

* 查看正在运行的容器  
    ``` editorconfig
    # 运行中的
    $ docker ps
    # 运行不运行的都查看
    $ docker ps -a
    ```

* 创建与启动容器
    ``` editorconfig
    $ docker run
    # 参数解释
    -i # 运行容器
    -t # 表示容器启动后会进入其命令行，加入这两个参数后，容器创建就能登录，即分配一个伪终端
    --name # 为容器命名
    -v # 表示目录映射关系（前者是宿主机目录，后者是映射到宿主机上的目录），可以使用多个-v 做多个目录或文件映射
       # 注意，最好做目录映射，在宿主机上修改，然后共享到容器上。
    -d # 在run后面加上 -d 参数，则会创建一个守护式容器在后台运行
    -p # 表示端口映射，前者是宿主机端口，后者是容器内的端口映射，可以使用多个 -p 做多个端口映射
    ```
    - 交互式创建容器  
        ``` editorconfig
        $ docker run -it --name=容器名称 镜像名称:标签 /bin/bash
        $ docker run -it --name=myredis redis /bin/bash
        $ docker run -it --name=mycentos centos:7 /bin/bash
        # 此时我们通过 ps 可以看到容器已启动
        # 退出容器
        $ exit # 交互式方式，退出时容器就停止了
        ```
    - 守护式创建容器
        ``` editorconfig
        $ docker run -di --name=容器名称 镜像名称:标签
        $ docker run -di --name=myredis2 redis
        # 登录守护式创建的容器
        $ docker exec -it 容器名称(或id) /bin/bash
        $ docker exec -it myredis2 /bin/bash
        ```
* 容器的停止与启动
    ``` editorconfig
    # 停止容器
    $ docker stop 容器名称(id)
    $ docker stop myredis
    
    # 启动容器
    $ docker start 容器名称(id)
    $ docker start myredis
  
    # 删除容器
    # 删除之前，需要先停掉对应容器
    $ docker rm 容器名称(id)
    $ docker rm myredis
    ```
* 文件拷贝
    ``` editorconfig
    # 拷贝进容器
    $ docker cp 拷贝的文件或目录 容器名称:容器目录
    $ docker cp /etc/java redis:/bin
    
    # 拷贝出容器
    $ docker cp 容器名称:容器目录 拷贝的文件或目录 
    $ docker cp redis:/bin /etc/java 
    ```
* 目录挂载
    ``` editorconfig
    # 我们可以在创建容器的时候，将宿主机的目录与容器内目录进行映射，
    # 这样就可以通过修改宿主机目录，而影响容器。修改容器也可影响宿主机
    
    # 创建容器添加 -v 参数, 宿主机目录:容器目录
    $ docker run -di -v /usr/local/java:/usr/local/java --name=myredis redis
    ```
## 应用部署

#### mysql 部署

* 步骤
    ``` editorconfig
    # 拉取 mysql 镜像
    $ docker pull mysql
    
    # 创建容器
    $ docker run -di --name=mysql1 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql
    #                   容器名称     端口映射        设置初始化 root 密码
    
    # 即可使用 Navicat 连接了
    ```

#### tomcat 部署

* 步骤
    ``` editorconfig
    # 拉取镜像
    $ docker pull tomcat
    
    # 创建容器
    $ docker run -di -p 8080:8080 --name=mytomcat -v /usr/local/webapps:/usr/local/tomcat/webapps tomcat
    #                               容器名称        目录挂载
    ```

#### nginx 部署
* 步骤
    ``` editorconfig
    # 拉取镜像
    $ docker pull nginx
    
    # 创建容器
    $ docker run -di -p 80:80 --name=mynginx nginx
    ```
  
#### redis 部署
* 步骤
``` editorconfig
# 拉取镜像
$ docker pull redis

# 创建容器
$ docker run -di -p 6379:6379 --name=myredis redis

# 连接测试 redis
# 在本机执行 redis-cli.exe 即可连接
[d:/redis]$ redis-cli.exe -h 192.168.52.129
 
```

## 迁移与备份
``` editorconfig
# 保存容器为镜像
$ docker commit 容器名称 镜像名称
$ docker commit mynginx mynginx_i

# 使用新镜像创建容器
$ docker run -di -p 81:80 --name=mynginx2 mynginx_i

# 镜像备份
$ docker save -o 文件名 镜像名
$ docker save -o mynginx.tar mynginx_i
# 当前目录就多一个 mynginx.tar 文件

# 镜像恢复
$ docker load -i 镜像文件
$ docker load -i mynginx.tar

```

## Dockerfile

#### 定义

* Dockerfile 是由一系列命令和参数构成的脚本，这些命令应用于基础镜像并最终创建一个新的镜像
* 对于开发人员，可以为开发团队提供一个完全一致的开发环境

#### 常用命令
| 命令 | 作用 |
|:----|:---- |
| FROM image_name:tag | 定义了使用哪个基础镜像启动构建流程 |
| MAINTAINER user_name | 声明镜像的创建者 |
| ENV key value | 设置环境变量（可写多条） |
| RUN command | 是Dockerfile的核心部分（可写多条） |
| ADD source_dir/file dest_dir/file | 将宿主机的文件复制到容器内，如果是一个压缩文件，将会在复制后自动解压 |
| COPY source_dir/file dist_dir/file | 和 ADD 类似，但如果有压缩文件不解压 |
| WORKDIR path_dir | 设置工作目录 |

#### 使用脚本创建镜像
* 步骤
``` editorconfig
# 创建 Dockerfile 文件，并填写信息
FROM centos:7  # 基础环境 Cent OS 7
MAINTAINER com.joush # 创建者姓名
WORKDIR /usr    # 工作目录
RUN mkdir/usr/local/java # 创建一个文件夹 
ADD jdk-8u171-linux-x64.tar.gz /usr/local/java/ # 将jdk加入目录中

# 在里面添加 jdk jre path 环境变量
ENV JAVA_HOME /usr/local/java/jdk.1.8.0_171
ENV JRE_HOME $JAVA_HOME/jre
ENV CLASS_PATH $JAVA_HOME/bin/dt.jar:$JAVA_HOME/lib/tools.jar:$JRE_HOME/lib:$CLASS_PATH
ENV path $JAVA_HOME/bin:$PATH

# 运行命令
$ docker build -t="镜像名称" Dockerfile文件目录
$ docker build -t="jdk1.8" . 

# 查看构建是否成功
$ docker images

```

#### 构建私有仓库
* 步骤
``` editorconfig
# 拉取私有仓库镜像
$ docker pull registry

# 启动私有仓库容器
$ docker run -di --name=registry -p 5000:5000 registry

# 查看是否构建成功
浏览器访问 http://192.168.52.129:5000/v2/_catalog 看到 
{"repositories" : []} 表示成功且内容为空

# 修改 daemon.json 配置文件
$ vi /etc/docker/daemon.json

# 添加内容,让 docker 信任私有仓库地址
{"insecure-registries":["192.168.52.129:5000"]}

# 重启 docker 服务
$ systemctl restart docker
```

#### 上传镜像至私有仓库

* 步骤
``` editorconfig
# 标记此镜像为私有仓库镜像
$ docker tag 镜像名称  私服地址
$ docker tag jdk1.8 192.168.52.129:5000/jdk1.8

# 上传标记的镜像
$ docker push 192.168.52.129:5000/jdk1.8

# 在另一台主机上下载私服镜像，需先配置 docker 信任私有仓库
$ docker pull 192.168.52.129:5000/jdk1.8
```

