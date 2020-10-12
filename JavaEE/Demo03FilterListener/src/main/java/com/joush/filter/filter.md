## MVC开发模式
#### Model 模型，JavaBean
* 完成具体的业务操作，如查询数据库，封装对象
#### View 视图，Jsp
* 展示数据
#### Controller 控制器，Servlet
* 获取请求参数
* 调用模型
* 将数据交给视图展示

##Filter 过滤器
####概念
* Web中的过滤器
    - 当服务器访问资源时，过滤器可以将请求拦截下来，完成一些特殊的功能
* 过滤器的作用
    - 一般用于完成通过的操作，如：登录验证，统一编码处理，敏感字符过滤
####快速入门
* 步骤
    - 定义一个类，实现接口 Filter
    - 复写方法
    - 配置拦截路径
        - web.xml配置
        - 注解配置
* 过滤器细节
    - web.xml配置
    ```
    <filter>
        <filter-name>demo01</filter-name>
        <filter-class>com.joush.filter.FilterDemo01</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>demo01</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ```
    - 过滤器执行流程
    - 过滤器生命周期方法
        - init, 在服务器启动后，会创建 Filter 对象，调用 init 方法，只执行一次，加载资源
        - doFilter, 每一次请求被拦截资源时，会执行，执行多次
        - destroy, 在服务器关闭时，Filter对象会被销毁，如果服务器正常关闭，则执行 destroy 方法
    - 过滤器配置详解
        - 拦截路径配置
            - 具体资源路径 /index.jsp 只有访问 index.jsp时，过滤器被执行
            - 拦截目录 /user/* 访问 /user 下的所有资源都被执行
            - 后缀名拦截方式 *.jsp 访问所有 jsp 资源都被执行
            - 拦截所有资源 /* 访问所有资源都会被执行
        - 拦截方式配置
            - 注解配置，设置 dispatcherTypes属性
                - REQUEST: 默认值。浏览器直接请求资源
                - FORWARD: 转发访问资源
                - INCLUDE: 包含访问资源
                - ERROR: 错误跳转资源
                - ASYNC: 异步访问
            - web.xml配置
                - 设置 <dispatcher></dispatcher>标签
    - 过滤器链(配置多个过滤器)
        - 执行顺序，两个过滤器
            - 过滤器1执行
            - 过滤器2执行
            - 资源
            - 过滤器2回来
            - 过滤器1回来
        - 过滤器先后顺序问题
            - 注解配置，按照类名字符串比较规则，值小的先执行
                - AFilter和BFilter，A先执行
               