<%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/12/6
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--  常用注解  --%>
    <a href="annotation/testRequestParam?name=haha">RequestParam 注解</a>
    <br>
    <br>

    <label>RequestBody 注解</label>
    <form action="annotation/testRequestBody" method="post"><br>
        姓名：<input type="text" name="username"><br>
        年龄：<input type="text" name="age"><br>

        <input type="submit" value="提交"><br>
    </form>
    <br>
    <br>

    <a href="annotation/testPathVariable/10">PathVariable 注解</a>
    <br>
    <br>

    <a href="annotation/testRequestHeader">RequestHeader 注解</a>
    <br>
    <br>

    <a href="annotation/testCookieValue">CookieValue 注解</a>
    <br>
    <br>

    <label>ModelAttribute 注解</label>
    <form action="annotation/testModelAttribute" method="post"><br>
        姓名：<input type="text" name="name"><br>
        年龄：<input type="text" name="age"><br>

        <input type="submit" value="提交"><br>
    </form>
    <br>
    <br>

    <a href="annotation/testSessionAttribute">SessionAttribute 注解</a>
    <a href="annotation/getSessionAttribute">Get SessionAttribute</a>
    <a href="annotation/delSessionAttribute">Del SessionAttribute</a>

</body>
</html>
