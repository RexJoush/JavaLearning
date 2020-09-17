##会话技术
####会话，一次会话包含多次请求和响应
* 一次会话：浏览器第一次给服务器资源发送请求，会话建立，直到有一方断开为止
* 功能，共享数据。在一次会话中共享数据
* 方式
    - 客户端会话技术: Cookie
    - 服务端会话技术: Session

##Cookie
####概念，客户端会话技术
####快速入门
* 步骤
    - 1.创建Cookie对象，绑定数据  
    `new Cookie(String name, String value);`
    - 2.发送Cookie对象  
    `response.addCookie(Cookie cookie);`
    - 3.获取Cookie，拿到数据  
    `Cookie[] cookies = request.getCookies();`
####实现原理
* 基于响应头set-cookie和请求头cookie实现

####cookie的细节
* 一次可否发送多个cookie
    - 可以
    ```
    Cookie c1 = new Cookie("msg1", "hello");
    Cookie c2 = new Cookie("msg2", "world");
    response.addCookie(c1);
    response.addCookie(c2);
    ```
    - 可以创建多个Cookie对象，多次调用addCookie方法即可
* cookie在浏览器中保存多长时间
    - 默认情况下，当浏览器关闭后，cookie资源被释放
    - 设置Cookie的生命周期，持久化存储  
    ```
    setMaxAge(int seconds);
    // 正数，将Cookie数据写到硬盘中，持久化存储，代表Cookie的存活时间
    // 负数，默认值，即关闭浏览器就清除
    // 零值，删除Cookie信息
    ```
    
* cookie能否存储中文
    - 在tomcat8之前，Cookie中不能直接存储中文数据
        - 需要将中文转码，一般采用URL编码(%E3)
    - 在tomcat8之后，可以存储中文数据
* Cookie是否能够共享
    - 在一个tom服务器中，部署了多个Web项目，这些项目的cookie能否共享
        - 默认情况下，两个项目，Demo01和Demo02不能共享
        - setPath(String path): 设置cookie的获取范围，默认情况下，会设置当前的虚拟目录
        - setPath("/"): 设置获取范围为根目录，则所有项目都可以共享
    - 不同的tomcat服务器间共享cookie问题
        - setDomain(String path); 设置一级域名相同，那么多个服务器直接cookie可以共享  
        `setDomain(".baidu.com"); // 此时 tieba.baidu.com 和 news.baidu.com 中的cookie可以共享`
* Cookie的特点
    - cookie存储数据在客户端浏览器
    - 浏览器对于单个cookie的大小有限制（4kb左右），以及对于同一个域名下的总cookie数量也有限制（20个以内）
    - 作用
        - cookie一般用于存储少量的不太敏感的数据
        - 在不登录的情况下，完成服务器对客户端的身份识别