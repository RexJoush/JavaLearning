## Docker

#### 安装 Docker
* 官方建议是用 Ubuntu，Cent OS 建议7以上版本
* yum 包更新到最新  
`$ sudo yum update` 
* 安装需要的软件包，yum-util 提供 yum-config-manger 功能，另外两个是 device mapper 启动依赖的  
`$ sudo yum install yum-utils device-mapper-persistent-data lvm2`
* 设置 yum 源为阿里云(可选)  
`$ sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo`
* 安装 Docker 社区版  
`$ sudo yum install docker-ce`
* 安装后查看 docker 版本  
`$ docker -v`

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
* 启动  
`$ systemctl start docker`
* 查看服务  
`$ systmectl status docker`
* 停止  
`$ systemctl stop docker`
* 查看信息  
`$ docker info`
* 帮助文档  
`$ docker --help`

## Docker 常用命令

#### 镜像相关命令

* 查看安装的镜像  
``` shell script
$ docker images 
REPOSITORY  TAG     IMAGE_ID  CREATED   SIZE
镜像名称     版本     镜像id    创建时间   大小
```

* 搜索镜像
```
$ docker search xxx
NAME    DESCRIPTION     STARS   OFFICAL     AUTOMATED
镜像名称    描述          星数    是否官方     是否由 Docker Hub自动构建流程创建的
```

* 拉取镜像
`$ docker pull xxx`

* 删除镜像
`$ docker rmi xxx // 镜像名称或id`

* 删除所有镜像
`$ docker rmi /`docker images -q /` `

