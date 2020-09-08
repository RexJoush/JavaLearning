## Response
    1.数据格式
        - 响应行
            1.组成 协议/版本 响应状态码 状态码描述
            2.状态码，服务器告诉客户端本次请求和响应的状态
                状态码都是三位数字
                - 1xx 服务器接受客户端消息，但没有接受完成，等待一段时间，发送 1xx
                - 2xx 请求成功。代表 200
                - 3xx 重定向。302（重定向） 304（访问缓存）
                - 4xx 客户端错误。404（路径资源不存在） 405（请求方式没有对应的 doXxx方法）
                - 5xx 服务端错误。500（服务器内部异常）
        - 响应头
            1.格式 响应头名称: 值
                - Content-Type 响应体的数据格式和编码格式
                - Content-Length 响应数据长度
                - Content-disposition 服务器告诉客户端以什么格式打开响应体
                    值
                        - in-line: 默认值，在当前页面打开
                        - attachment: 以附件形式打开响应体。下载文件一般会使用
        - 响应空行
        - 响应体
            发送的字符串数
    2.Response 对象
        - 设置响应行
            1.格式 HTTP/1.1 200 OK
            2.设置状态码 setStatis(int sc)
        - 设置响应头 setHeader(String name, String value)
        - 设置响应体 
            1.使用步骤
                - 获取输出流
                    字符输出流 PrintWriter getWriter()
                    字节输出流 Serv;etOutputStream getOutputStream()
                - 使用输出流，将数据输出到客户端浏览器
                
    3.案例