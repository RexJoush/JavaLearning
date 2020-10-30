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