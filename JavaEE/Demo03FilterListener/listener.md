## 监听器
#### 概念，web的三大组件之一
* 事件的监听机制
    - 事件，一件事情
    - 事件源，事件发生的地方
    - 监听器，一个对象
    - 注册监听，将事件，事件源，监听器绑定一起
#### ServletContextListener
* 方法
``` java
void contextDestoryed(ServletContextEvent sce);  // ServletContext 对象被销毁之前会调用该方法
void contextInitialized(ServletContextEvent sce); // ServletContext 对象创建后会调用该方法
```
* 步骤
    - 定义一个类，实现 ServletContextListener 接口
    - 复写方法
    - 配置
        - web.xml
            ``` xml
            <listener>
                <listener-class>com.joush.listener.ListenerDemo01ContextLoaderListener</listener-class>
            </listener>
           
            // 指定初始化参数
            <context-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>/WEB-INF/classes/application.xml</param-value>
            </context-param>
            ```
        - 注解配置  
            `@WebListener`
            