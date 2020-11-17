## request 继承关系
```
ServletRequest      -- 接口
       | 继承
HttpServletRequest  -- 接口
       | 实现
org.apache.catalina.connector.RequestFacade (tomcat实现的)
```

## request 功能
#### 获取请求数据
- 获取请求行数据
    * GET /Demo1/demo01?name=tom HTTP/1.1
    * 方法
        - 获取请求方式： GET  
            `String getMethod(); `
        - 获取虚拟目录 /Demo1  
            ` String getContextPath(); `
        - 获取Servlet路径 /demo01  
            `String getServletPath(); `
        - 获取get方式请求参数 name=tom  
            `String getQueryString(); `
        - 获取请求的uri /Demo1/demo01  
            ``` java
            String getRequestURI();
            StringBuffer getRequestURL(); // http://localhost:8080/Demo1/demo01
            
            URL: 统一资源定位符 http://localhost:8080/Demo1/demo01
            URI: 统一资源标识符 /Demo1/demo01
          ```
        - 获取协议及版本 HTTP/1.1  
            `String getProtocol();`
        - 获取客户机的IP地址  
            `String getRemoteAddr(); `
- 获取请求头数据
    * 方法
        ``` java
        String getHeader(String name); 通过请求头的名称获取请求头的值
        Enumeration<String> getHeaderNames(); 获取所有请求头名称
        ```
- 获取请求体数据，请求体只有 POST 请求才有
    * 步骤
        - 获取流对象  
          ``` java 
          BufferedReader getReader(); 获取字符输入流，只能操作字符数据
          ServletInputStream getInputStream(); 获取字节输入流，可以操作所有类型数据
          ```
        - 从流对象获取数据
        ``` java
        // 2.读取数据
        String line = null;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }
        ```
#### 其他功能
- 获取请求参数通用方式
    ``` java
    String getParameter(String name); 根据参数名获取参数值
    String[] getParameterValues(String name); 根据参数名获取参数值数组 hobby=movie&hobby=watch
    Enumeration<String> getParameterNames(); 获取所有参数的名称
    Map<String,String[]> getParameterMap(); 获取所有参数的键值对 map 集合
    ```

    * 中文乱码问题
        - 在 post 请求前面加一句 `req.setCharacterEncoding("UTF-8");`
- 请求转发，一种在服务器内部资源跳转的方式
  * 步骤
      - 通过 request 获取请求转发对象 RequestDispatcher 
      `getRequestDispatcher(String path);`
      - RequestDispatcher对象调用 forward 方法  
      `void forward(ServletRequest res, ServletResponse resp)`
  * 特点
      - 浏览器地址栏不发生变化
      - 只能转发到当前服务器内部资源
      - 多个资源中使用的是同一次请求
#### 共享数据
- 域对象，一个有作用范围对象
- request域，代表一次请求的范围，一般用于请求转发的多个资源中共享数据
- 方法
    ``` java
    setAttribute(String name, Object obj); // 存储数据
    getAttribute(String name); // 通过键值来获取数据
    removeAttribute(String name); // 通过键来移除键值对
    ```

#### 获取 ServletContext
- 获取ServletContext对象  
    `ServletContext getServletContext();`