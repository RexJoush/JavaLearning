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
        # 如果出现 No resources found. 查看后面错误解决 1
            
        $ kubectl create -f mytomcat-svc.yaml
        # service "mytomcat" created 创建成功
        $ kubectl get svc
        # 出现结果说明正确
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
        - docker pull 失败
            ``` editorconfig
            # 解决方案 1
            $ yum install rhsm
            $ docker pull register.access.redhat.com/rhel7/pod-infrastructure:lastest
            ```