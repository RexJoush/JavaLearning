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
    
## Kubernetes 集群, 自动化部署
* kubernetes 集群部署有三种方式，kubeadm，minikube 和二进制包，前两种属于自动部署，可实现快速部署

#### 环境准备与规划
* 推荐配置 2核2G
* | 角色 | IP  | 组件 |
  |:----|:----|:---- |
  |master| 192.168.52.136| etcd, kube-apiserver, kube-controller, kube-scheduler, docker|
  |node1 | 192.168.52.137| kube-proxy, kubelet, docker|
  |node2 | 192.168.52.138| kube-proxy, kubelet, docker|

#### 步骤

* 系统初始化（Master，Node 均需要进行）
``` editorconfig

# 关闭防火墙
$ systemctl stop firewalld
$ systemctl disable firewalld

# 关闭 selinux
$ sed -i 's/enforcing/disabled/' /etc/selinux/config # 永久关闭
$ setenforce 0 # 临时关闭

# 关闭swap
$ swapoff -a     # 临时关闭
$ vim /etc/fstab # 永久关闭

# 修改主机名
$ hostnamectl set-hostname master # 在 master 上操作
$ hostnamectl set-hostname node1 # 在 node1 上操作
$ hostnamectl set-hostname node2 # 在 node2 上操作

# 添加 hosts, 在 msater 上添加
# 此处的 ip 地址需要和对应的主机相匹配，主机名需要和上一步设置的相匹配
$ cat >> /etc/hosts << EOF
    192.168.52.136 master
    192.168.52.137 node1
    192.168.52.138 node2
    EOF

# 将桥接的IPv4 流量传递到iptables 的链
$ cat > /etc/sysctl.d/k8s.conf << EOF
    net.bridge.bridge-nf-call-ip6tables = 1
    net.bridge.bridge-nf-call-iptables = 1
    EOF
$ sysctl --system  # 使之生效

# 配置时间同步
$ yum install ntpdate -y
$ ntpdate time.windows.com
```

* 在所有节点上安装 Docker kubeadm kubelet（Master，Node 均需要进行）
``` editorconfig
# 安装 docker
$ wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo
    # 如果提示 wget 未找到，则先安装 wget
    $ yum install wget -y

# 下载 docker
$ yum -y install docker-ce-18.06.1.ce-3.el7
# 启动 docker
$ systemctl enable docker && systemctl start docker
# 查看 docker 版本
$ docker --version

# 添加阿里云 yum 源
$ cat > /etc/docker/daemon.json << EOF
    {
        "registry-mirrors": ["https://b9pmyelo.mirror.aliyuncs.com"]
    }
    EOF

$ vi /etc/yum.repos.d/kubernetes.repo
# 添加下面内容
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg

# 安装 kubeadm kubelet 和 kubectl
$ yum install -y kubelet kubeadm kubectl

# 启动 kubelet
$ systemctl enable kubelet
```

* 部署 Master （仅 master 需要进行）
``` editorconfig
# 初始化集群环境
$ kubeadm init --apiserver-advertise-address=192.168.52.136 --image-repository registry.aliyuncs.com/google_containers --kubernetes-version v1.19.4 --service-cidr=10.96.0.0/12 --pod-network-cidr=10.244.0.0/16
    # 此处如果提示版本错误，就把参数中 --kubernetes-version v1.19.4 改为错误提示中的版本

# 初始化完成后，会有如下的提示

# To start using your cluster, you need to run the following as a regular user:
#   mkdir -p $HOME/.kube
#   sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
#   sudo chown $(id -u):$(id -g) $HOME/.kube/config
# 
# You should now deploy a pod network to the cluster.
# Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
#   https://kubernetes.io/docs/concepts/cluster-administration/addons/
# 
# Then you can join any number of worker nodes by running the following on each as root:
# 
# kubeadm join 192.168.52.136:6443 --token wlaft6.hql5b1n452wbh1du \
#     --discovery-token-ca-cert-hash sha256:5458858a613822ef8cb7e1c0bc148c232081c990d2a3e2b504b7bc53b4be7c52

# 根据提示中的三条命令，执行

$ mkdir -p $HOME/.kube
$ sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
$ sudo chown $(id -u):$(id -g) $HOME/.kube/config

# 查看集群状态
$ kubectl get nodes

# 查看到下面内容，即表示成功，此时所有节点暂时处于 NotReady 状态
NAME     STATUS     ROLES    AGE     VERSION
master   NotReady   master   2m30s   v1.19.4
```

* node 节点加入集群（所有 node 需要进行）
``` editorconfig
# 加入集群，执行上面 master 节点完成最后面的提示信息，即加入完成
kubeadm join 192.168.52.136:6443 --token wlaft6.hql5b1n452wbh1du --discovery-token-ca-cert-hash sha256:5458858a613822ef8cb7e1c0bc148c232081c990d2a3e2b504b7bc53b4be7c52
```

* 查看集群配置（在 master 中进行）
``` editorconfig
# 查看集群状态，打印下面信息表示集群创建成功
$ kubectl get nodes
NAME     STATUS     ROLES    AGE   VERSION
master   NotReady   master   21m   v1.19.4
node1    NotReady   <none>   4s    v1.19.4
```
* 配置网络插件 CNI，使得集群能够互相访问（在 master 中进行）
``` editorconfig
# 安装 cni，因为访问外网，如果出现错误，多尝试几次即可
$ kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

# 查看集群状态，当下面的所有都变成 READY 1/1 即表示完成
$ kubectl get pods -n kube-system
NAME                             READY   STATUS     RESTARTS   AGE
coredns-6d56c8448f-2b5sz         0/1     Pending    0          27m
coredns-6d56c8448f-l2fq7         0/1     Pending    0          27m
etcd-master                      1/1     Running    0          27m
kube-apiserver-master            1/1     Running    0          27m
kube-controller-manager-master   1/1     Running    0          27m
kube-flannel-ds-psbzk            1/1     Running    0          26s
kube-flannel-ds-vgmp7            0/1     Init:0/1   0          26s
kube-proxy-9wb64                 1/1     Running    0          6m22s
kube-proxy-dhxb6                 1/1     Running    0          27m
kube-scheduler-master            1/1     Running    0          27m

# 继续查看 node 集群，可看到所有节点均变成 Ready 状态
$ kubectl get nodes
NAME     STATUS   ROLES    AGE     VERSION
master   Ready    master   27m     v1.19.4
node1    Ready    <none>   6m48s   v1.19.4
```

* 集群通信测试（在 master 中进行）
``` editorconfig
# 在 master 节点上搭建 nigix 服务
$ kubectl create deployment nginx --image=nginx

# 查看状态,等待 nginx 下载完成后，表示成功
$ kubectl get pod
NAME                     READY   STATUS    RESTARTS   AGE
nginx-6799fc88d8-ng84n   1/1     Running   0          41s

# 查看 nginx 端口信息
$ kubectl get pod,svc

# 打印下面信息，可以看到 nginx 运行在 31142 端口
pod/nginx-6799fc88d8-ng84n   1/1     Running   0          79s

NAME                 TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)        AGE
service/kubernetes   ClusterIP   10.96.0.1     <none>        443/TCP        30m
service/nginx        NodePort    10.99.57.60   <none>        80:31142/TCP   29s

```
* 通过 node 节点进行访问（在任一 node 中进行）
``` editorconfig
# 在任一 node 节点上访问 nginx
$ curl http://192.168.52.136:31142 # 此处的 ip 地址为 master ip，端口号为上一步查看到的端口号

# 查看到 nginx 的欢迎界面即表示成功，此处通过本机浏览器访问也可实现
```

## Kubernetes 集群, 二进制部署
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
                - Debugging Tools
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

