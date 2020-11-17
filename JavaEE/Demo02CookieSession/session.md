## Session
#### 概念，服务器端会话技术，将数据保存在服务器对象中，HttpSession
#### 快速入门
* HttpSession对象
    ``` java
    Object getAttribute(String name);
    void setAttribute(String name, Object value);
    void removeAttribute(String name);
    ```
* 获取HttpSession对象  
    ``` java
    HttpSession session = request.getSession();
    ```
* 使用HttpSession对象  
    ``` java
    session.setAttribute("msg", "Hello Session"); // 设置session
    Object obj = session.getAttribute("msg"); // 获取session
    ```
#### 原理
* Session的实现是基于Cookie的

#### 细节
* 当客户端关闭后，服务器不关闭，两次获取session是否为同一个？
    - 默认情况下不是
    - 如果需要， 则可以设置最大存活时间
    ``` java
    Cookie cookie = new Cookie("JSESSIONID",session.getId());
    cookie.setMaxAge(60*60); // 一个小时
    response.addCookie(cookie);
    ```
* 客户端不关闭，服务器关闭后，两次获取的session是同一个吗
    - 不是同一个
    - session的钝化
        - 在服务器正常关闭前，将session对象系列化到硬盘上
    - session的活化
        - 在服务器启动后，将session文件转化为内存中的session对象
* session什么时候被销毁
    - 服务器关闭 
    - session对象调用调用invalidate()
    - session默认失效时间30分钟，可以配置
#### 特点
* session是用于存储一次会话多次请求的数据，存在服务器端
* session可以存储任意类型，任意大小的数据