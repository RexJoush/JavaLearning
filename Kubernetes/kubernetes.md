## Kubernetes 快速入门

#### 环境准备
``` editorconfig
# 关闭防火墙
$ systemctl disable firewalld
$ systemctl stop firewalld

# 安装 etcd 和 kubernetes
$ yum install etcd kubernetes -y

# 启动服务
$ systemctl start etcd
$ systemctl start docker

    # 如果 docker 启动失败, 
    $ vi /etc/sysconfig/selinux
    将selinux 改为 disabled，重启
    $ reboot

$ systemctl start kube-apiserver
$ systemctl start kube-controller-manager
$ systemctl start kube-scheduler
$ systemctl start kube-proxy
$ systemctl start kubelet
```
#### 配置

* tomcat 配置
    - 添加以下两个配置文件
    ``` editorconfig
    # 创建文件夹  
    $ mkdir /usr/local/kubernetes
    $ cd /usr/local/kubernetes
    
    # 此处注意，内容一定保证一样，空格缩进一定要一样，不然会出错，单行缩进一个空格，注意！！！
    $ vi mytomcat-rc.yaml
    $ vi mytomcat-svc.yaml
    ```
    - mytomcat-rc.yaml
        ``` yaml
        apiVersion: v1
        kind: ReplicationController
        metadata:
         name: mytomcat
        spec: 
         replicas: 2
         selector:
          app: mytomcat
         template:
          metadata:
           labels:
            app: mytomcat
          spec:
           containers:
            - name: mytomcat
              image: tomcat
              ports:
              - containerPort: 8080
        ```
    - mytomcat.svc.yaml
        ``` yaml
        apiVersion: v1
        kind: Service
        metadata:
         name: mytomcat
        spec: 
         type: NodePort
         ports:
          - port: 8080
            nodePort: 30001
         selector:
          app: mytomcat
        ```
  
    - 执行命令
        ``` editorconfig
        $ kubectl create -f mytomcat-rc.yaml
        # replicationcontroller "mytomcat" created 创建成功
            
        $ kubectl get pods # 查看结果
        # 如果出现 No resources found. 查看后面错误解决
            
        $ kubectl create -f mytomcat-svc.yaml
        # service "mytomcat" created 创建成功
        $ kubectl get svc
        # 出现结果说明正确
        
        # 外部访问
        # 浏览器能访问到 tomcat 即表示成功
        http://192.168.xx.xx:30001
        # 死活访问不到，就这吧，放弃了
        ```
    - 错误解决
        - kubectl get pods 出现 No resources found.
            ``` editorconfig
            # 1.编辑文件
            $ vi /etc/kubernetes/apiserver
            # 2.找到 
            "KUBE_ADMISSION_CONTROL="--admission_control=NamespaceLifecycle,NamespaceExits,LimitRanger,SecurityContextDeny,ServiceAccount,ResourceQuoya",
            去掉 ServiceAccount,保存退出
            # 3.重启服务
            systemctl restart kube-apiserver
            # 4.查看结果即可
            $ kubectl get pods
            
            ```
        - docker pull 失败，即 kubectl get pods 一直显示 ContainerCreating，Ready 0/1 并没有跑起来
            ``` editorconfig
            # 解决方案 1
            1. $ yum install *rhsm*
            # 此处，如果提示找不到 rhsm，那么可能是虚拟机装机时少勾选了一些东西
            # 找新的仓库很费劲，建议重装虚拟机，并在安装时勾选工具，具体哪个起作用我也不知道，所以都装上
                - Debuging Tools
                - Development Tools
                - System Administration Tools
            2. $ docker pull register.access.redhat.com/rhel7/pod-infrastructure:lastest
          
            # 解决方案 2
            1. $ wget http://mirror.centos.org/centos/7/os/x86_64/Packages/python-rhsm-certificates-1.19.10-1.el7_4.x86_64.rpm
            2. $ rpm2cpio python-rhsm-certificates-1.19.10-1.el7_4.x86_64.rpm | cpio -iv --to-stdout ./etc/rhsm/ca/redhat-uep.pem | tee /etc/rhsm/ca/redhat-uep.pem
            3. $ systemctl restart kubelet
            # 等一会看看会不会好
          
            # 解决方案3
            1. $ docker pull kubernetes/pause
            2. $ docker tag docker.io/kubernetes/pause:latest 192.168.52.132:5000/google_containers/pause-amd64.3.0
            3. $ docker push 192.168.xx.xx:5000/google_containers/pause-amd64.3.0
            4. $ vi /etc/kubernetes/kubelet # 修改配置
                 KUBELET_ARGS="--pod_infra_container_image=192.168.xx.xx:5000/google_containers/pause-amd64.3.0"
            5. $ systemctl restart kubelet 
            
            # 三种方案如果都不行，那就不知道了.... 我也是稀里糊涂弄好的
            ```
        - 外部网不能访问
            ``` editorconfig
            # 搭建好 k8s 集群内创建的容器，只能在其所在的节点上访问，不能在其他主机上访问
            1. $ vi /etc/sysctl.conf
            # 在后面添加一句
            2. $ net.ipv4.ip_forward=1
            ```

## Kubernetes 的基本架构与常用术语

#### Kubernetes的核心组件

* etcd
    - 保存了整个集群的状态
* apiserver
    - 提供了资源操作的唯一入口，并提供认证，授权，访问控制，API注册和发现等机制
* controller manager
    - 负责维护集群的状态，比如故障检测、自动扩展、滚动更新等
* schedule
    - 负责资源的调度，按照预定的调度策略将 Pod 调度到相应机器上
* kubelet   
    - 负责维护容器的生命周期，同时负责 Volume(CVI) 和 网络 (CNI) 的管理
* Container runtime
    - 负责镜像管理以及 Pod 和容器的真正运行 (CRI)
* kube-proxy
    - 负责为 Service 提供 cluster 内部的服务发现和负载均衡
    
#### Cluster
* Cluster 是计算、存储和网络资源的集合，Kubernetes 利用这些资源运行各种基于容器的应用
* Kubernetes Cluster 由 Master 和 Node 组成，节点上运行着若干 Kubernetes 服务

#### Master
* Master 主要职责是调度，即决定将应用放在哪里运行。Master 运行 Linux 系统，可以是物理机或虚拟机。Master 是 Kubernetes Cluster 的大脑
* 运行着 Daemon 服务包括 kube-apiserver、kube-scheduler、kube-controller-manager、etcd 和 pod 网络
* pod 网络
    - pod 网络需要能够互相通信，Kubernetes Cluster 必须部署 Pod 网络，flannel 是其中一个可选方案
    
#### Node
* 除了 Master 以外，Kubernetes 集群中的其他节点称为 Node 节点。
* Node 负责运行容器应用，Node 由 Master 管理，Node 负责监控并汇报容器状况，根据 Master 管理容器的生命周期。Node 也运行在 Linux，可以是物理机或虚拟机
* 每个 Node 节点都运行一组关键进程
    - kubelet
        - 负责 Pod 对应容器的创建、启动等任务，同时与 Master 密切合作，实现集群管理的基本功能
    - kube-proxy
        - 实现 Kubernetes Service 的通信与负载均衡机制的重要组件
    - Docker Engine
        - Docker 引擎，负责本机的容器创建和管理工作

#### Pod
* Pod 是 Kubernetes 的最小单元，也是最重要最基本的概念。每个 Pod 包含一个或多个容器， Pod 的容器会作为一个整体被 Master 调度到一个 Node 上运行。
* Kubernetes 为每个 Pod 都分配了唯一的 IP 地址，称为 PodIP，一个 Pod 里的多个容器共享 PodIP 地址
* 在 Kubernetes 里，一个 Pod 里的容器与另外主机上的 Pod 容器能够直接通信

#### Service
* Kubernetes Service 定义了外界访问一组特定 Pod 的方式，Service 有自己的 IP 和端口， Service 为 Pod 提供了负责均衡
* 他是 Kubernetes 最核心的资源对象之一，每个 Service 其实就是我们经常提起的微服务架构中的一个微服务

#### Replication Controller
* Replication Controller (RC) 是 Kubernetes 系统中核心的概念之一他其实是定义了一个期望的场景，即声明某种 Pod 的副本数量在任意时刻都符合某个预期值，所以 RC 的定义包括如下几个部分
    - Pod 期待的副本数 (replicas)
    - 用于筛选目标 Pod 的 Label Selector
    - 当 Pod 的副本数量小于预期数量时，用于创建新 Pod 的 Pod 模板 (template)
* RC 的特性与作用
    - 在大多数的情况下，我们通过定义一个 RC 实现 Pod 的创建过程以及副本数量的自动控制
    - RC 包含完整的 Pod 定义模板
    - RC 通过 Label Selector 机制实现对 Pod 副本的自动控制
    - 通过改变 RC 里的副本数量，可以实现 Pod 的扩容或缩容功能
    - 通过改变 RC 里 Pod 模板中的镜像版本，可以实现 Pod 的滚动升级
    
## Kubernetes 集群
* kubernetes 集群部署有三种方式，kubeadm，minikube 和二进制包，前两种属于自动部署，简化部署操作，建议使用二进制包，利于学习

#### 环境准备与规划
* 推荐配置 2核2G
* | 角色 | IP  | 组件 |
  |:----|:----|:---- |
  |master| 192.168.52.133| etcd, kube-apiserver, kube-controller, kube-scheduler, docker|
  |node1 | 192.168.52.134| kube-proxy, kubelet, docker|
  |node2 | 192.168.52.135| kube-proxy, kubelet, docker|
* 步骤
``` editorconfig
# 查看默认防火墙状态（关闭显示 not running，开启显示 running）
$ firewall-cmd --state

# 关闭防火墙
$ systemctl stop firewalld.service

# 禁止 firewall 开机启动
$ systemctl disable firewalld.service

```
* 获取 kubernetes 二进制包
    - <https://github.com/kubernetes/kubernetes/blob/master/CHANGELOG/CHANGELOG-1.18.md#v11810>
    - 找到 Server Binaries,下载 kubernetes-server-linux-amd64.tar.gz
    
#### Master 安装

* docker 安装
    <!--- 
    # 设置 yum 源
    ``` editorconfig
    $ vi /etc/yum.repos.d/docker.repo
    [dockerrepo]
    name=Docker Repository
    baseurl=https://yum.dockerproject.org/repo/main/centos/$releasever/
    enabled=1
    gpgcheck=1
    gpgkey=https://yum.dockerproject.org/gpg
    ```
    --->
    ``` editorconfig
    # 安装 docker
    $ yum install docker
    
    # 查看 docker 版本
    $ docker -v
    ```
    
* etcd 服务
    - etcd 作为 Kubernetes 集群的主要服务，在安装 Kubernetes 各服务之前需要首先安装和启动
    - 下载 etcd 二进制文件
        <https://github.com/etcd-io/etcd/releases>   
        etcd-v3.3.x-linux-amd64.tar.gz
    - 上传至 /usr/local/kubernetes, 此文件夹随意即可
    - 解压，并将 etcd 和 etcdctl 文件复制到 /usr/bin 目录下
        ``` editorconfig
        $ tar -xvf etcd-v3.3.25-linux-amd64.tar.gz
        $ cd etcd-v3.3.25-linux-amd64
        $ cp etcd etcdctl /usr/bin
        ```
    - 配置 systemd 服务文件 /usr/lib/systemd/system/etcd.service
        ``` editorconfig
        $ vi /usr/lib/systemd/system/etcd.service
        
        [Unit]
        Description=Etcd Server
        After=network.target
        [Service]
        Type=simple
        EnvironmentFile=-/etc/etcd/etcd.conf
        WorkingDirectory=/var/lib/etcd/
        ExecStart=/usr/bin/etcd
        Restart=on-failure
        [Install]
        WantedBy=multi-user.target
        ```
    - 启动与测试服务
        ``` editorconfig
        $ mkdir /var/lib/etcd # 创建出来工作目录
        $ systemctl daemon-reload
        $ systemctl enable etcd.service
        $ systemctl start etcd.service
        $ systemctl status etcd.service # 查看到 running 表示启动成功
        $ etcdctl cluster-health # 出现 cluster is healthy 说明成功
        ```

* kube-apiserver 服务
    - 下载 kubernetes-server-linux-amd64.tar.gz
    <https://kubernetes.io/docs/setup/release/notes/>
    - 将 kubernetes/server/bin 下的 kube-apiserver kube-controller-manager kube-scheduler kubectl 以及管理要使用的二进制命令文件放到 /usr/bin目录
    ``` editorconfig
    cp kube-apiserver kube-controller-manager kube-scheduler kubectl /usr/bin
    ```
    - 对 kube-apiserver 服务进行配置
    ``` editorconfig
    # 编辑 systemd 服务文件 
    $ vi /usr/lib/systemd/system/kube-apiserver.service
    
    # 添加内容
    [Unit]
    Description=Kubernetes API server
    Documentation=https://github.com/kubernetes/kubernetes
    After=etcd.service
    Wants=etcd.service
    [Service]
    EnvironmentFile=/etc/kubernetes/apiserver
    ExecStart=/usr/bin/kube-apiserver $KUBE_API_ARGS
    Restart=on-failure
    Type=notify
    [Install]
    WantedBy=multi-user.target
  
    # 配置文件，创建目录
    $ mkdir /etc/kubernetes
    $ vi /etc/kubernetes/apiserver
  
    # 添加内容
    KUBE_API_ARGS="--storage-backend=etcd3 --etcd-servers=http://127.0.0.1:2379 --insecure-bind-address=0.0.0.0
    --insecure-port=8080 --service-cluster-ip-range=169.169.0.0/16 --service-node-port-range=1-65535
    --admission-control=NamespaceLifecycle,NamespaceExists,LimitRanger,SecurityContextDeny,ServiceAccount,DefaultStorageClass,ResourceQuota
    --logtostderr=true --log-dir=/var/log/kubernetes --v=2"
    ```

* kube-controller-manager 服务
    - kube-controller-manager 依赖于 kube-apiserver 服务
    ``` editorconfig
    # 配置 systemd 服务文件
    $ vi /usr/lib/systemd/system/kube-controller-manager.service
    
    # 添加内容
    [Unit]
    Description=Kubernetes Controller Manager
    Documentation=https://github.com/GoogleCloudPlatform/kubernetes
    After=kube-apiserver.service
    Requires=kube-apiserver.service
    
    [Service]
    EnvironmentFile=/etc/kubernetes/controller-manager
    ExecStart=/usr/bin/kube-controller-manager $KUBE_CONTROLLER_MANAGER_ARGS
    Restart=on-failure
    LimitNOFILE=65536
  
    [Install]
    WantedBy=multi-user.target
    
    # 配置文件
    $ vi /etc/kubernetes/controller-manager
  
    # 添加内容
    KUBE_CONTROLLER_MANAGER_ARGS="--master=http://192.168.52.133:8080 --logtostderr=true --log-dir=/var/log/kubernetes --v=2"
    ```

* kube-scheduler 服务
    - kube-scheduler 也依赖于 kube-apiserver 服务
    ``` editorconfig
    # 配置 systemd 服务文件
    $ vi /usr/lib/systemd/system/kube-scheduler.service
    
    # 添加内容
    [Unit]
    Description=Kubernetes Scheduler
    Documentation=https://github.com/GoogleCloudPlatform/kubernetes
    After=kube-apiserver.service
    Requires=kube-apiserver.service
    
    [Service]
    EnvironmentFile=/etc/kubernetes/scheduler
    ExecStart=/usr/bin/kube-controller-manager $KUBE_SCHEDULER_ARGS
    Restart=on-failure
    LimitNOFILE=65536
  
    [Install]
    WantedBy=multi-user.target
    
    # 配置文件
    $ vi /etc/kubernetes/scheduler
  
    # 添加内容
    KUBE_SCHEDULER_ARGS="--master=http://192.168.52.133:8080 --logtostderr=true --log-dir=/var/log/kubernetes --v=2"
    ```  
* 启动
    - 完成上面配置后，开始启动服务
    ``` editorconfig
    # 挨个启动
    $ systemctl daemon-reload
    $ systemctl enable kube-apiserver.service
    $ systemctl start kube-apiserver.service
    $ systemctl enable kube-controller-manager.service
    $ systemctl start kube-controller-manager.service
    $ systemctl enable kube-scheduler.service
    $ systemctl start kube-scheduler.service
  
    # 检查状态
    $ systemctl status kube-apiserver.service
    $ systemctl status kube-controller-manager.service
    $ systemctl status kube-scheduler.service
    ```