##Session
####概念，服务器端会话技术，将数据保存在服务器对象中，HttpSession
####快速入门
* HttpSession对象
    ```
    Object getAttribute(String name);
    void setAttribute(String name, Object value);
    void removeAttribute(String name);
    ```
* 获取HttpSession对象  
    `HttpSession session = request.getSession();`
* 使用HttpSession对象  
    ```
    session.setAttribute("msg", "Hello Session"); // 设置session
    Object obj = session.getAttribute("msg"); // 获取session
    ```
####原理
* Session的实现是基于Cookie的