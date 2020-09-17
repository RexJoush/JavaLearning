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