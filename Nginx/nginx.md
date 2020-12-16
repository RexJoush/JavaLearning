## Nginx

#### Nginx + Tomcat 集群配置
* 在一台电脑上安装两个 tomcat
* 修改 tomcat 的配置文件，将端口进行修改
``` html
<!-- conf\server.xml -->

<!-- 修改三个地方 -->
<!-- 1.server 标签  -->
<Server port="8005" shutdown="SHUTDOWN">
<!-- 2  -->
<Connector port = "8080" protocol="HTTP/1.1"  -->
    connectionTimeout="20000"
    redirectPort="8443" />
<!-- 3  -->
<Connector port = "8009" protocol="AJP/1.3 redirctorPor="8443" />

```
* 将项目发布到两个tomcat上
* 安装 nginx
    - 解压，双击exe即安装成功
    - 打开 localhost:80 看到提示信息即代表安装成功

* 配置 nginx
    - 修改 `\nginx\conf\nginx.conf`
    ``` lombok.config
    # 1.在 server 之前添加 server 列表
    upstream serverlib {    # serverlib名字随便起
        server localhost:8080; # 可以添加权重信息 server localhost:8080 weight 4;
        server localhost:8081;               # server localhost:8081 weight 10;
        ip_hash;    # 保证一个用户每次只访问同一台服务器，解决session共享的问题
    }
  
    # 2.在 server 的 location 中添 proxy_pass
    server {
            listen       80;
            server_name  localhost; # 域名信息
            location / {
                root   html;
                proxy_pass http://serverlib; # 服务器列表名称
                index  index.html index.htm;
            }
            error_page   500 502 503 504  /50x.html;
            location = /50x.html {
                root   html;
            }
        }
    ```
* tomcat 集群的 session 共享问题
    - 一个用户只在其中一台进行操作
        - 在 serverlib 中加上一个参数，ip_hash;
    - 共享 session
        - 使用 tomcat 的广播机制，实现 session 共享，（不推荐）
            - 修改 `server.xml` 的内容
                ``` nginx
                <!-- 单集群，将 Engine 标签中的 Cluster 标签去掉注释即可  -->
                <Cluster className="org.apache.catalina.ha.tcp.SimpleTcpClister" />
                <!-- 如果是多集群，可以加上参数  -->
                <Cluster calssName="org.apache.catalina.ha.tcp.SimpleTcpClister">
                    <Channel className="org.apache.catalina.tribes.membership.McatService"
                            address="228.0.0.4"
                            port="45564"
                            frequency="500"
                            dropTime="3000"
                    </Channel>
                </Cluster>
                ```
            - 在 web.xml中加入配置
                `<distributable/>`
        - 使用 redis 服务器的方式实现，即所有 tomcat 服务器将所有 session 都存储到一个 redis 服务器中（推荐）

