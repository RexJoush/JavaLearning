##Response
####响应消息
* 数据格式
    - 响应行
        - 组成：协议/版本 响应状态码 状态码描述 HTTP/1.1 200 OK
    - 响应头
        - 格式 头名称: 值
        - 常见的响应头
            - Content-Type: 服务器告诉客户端本次响应体数据格式以及编码格式
            - Content-disposition: 服务器告诉客户端以什么格式打开响应体数据
                - 默认值，in-line，在当前页面打开
                - attachment: 以附件形式打开响应体，文件下载常用
    - 响应空行
    - 响应体
        - 传输的数据

####Response对象
* 功能，设置响应消息
    - 设置响应行
        - 格式: HTTP/1.1 200 OK 
        - 设置状态码  
        `setStatus(int sc);`
    - 设置响应头  
        `setHeader(String name, String value);`
    - 设置响应体
        - 获取输出流
        ```
        PringWriter getWriter(); // 字符输出流
        ServletOutputStream getOutputStream(); // 字节输出流
        ```
        - 使用输出流，将数据输出到客户端浏览器
        
##ServletContext对象
####概念，代表整个 Web 应用，可以和服务器通信
####获取
* 通过 request 对象来获取  
`request.getServletContext();`
* 通过 HttpServlet 获取  
`this.getServletContext();`
####功能
* 获取MIME类型
    - MIME类型，在互联网通信过程种定义的一种文件数据类型
        - 格式：大类型/小类型 text/html image/jpeg
    - 获取  
        - `String getMimeType(String file);`
* 域对象，共享数据
    ```
    setAttribute(String name, Object value);
    getAttribute(String name);
    removeAttribute(String name);
    ```
    * ServletContext对象范围，所有用户所有请求的数据
* 获取文件的真实路径
    * 方法  
    `String getRealPath(String path);`