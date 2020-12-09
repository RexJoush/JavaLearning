<%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/12/8
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>传统文件上传</h3>
    <a href="user/fileUpload1">test</a>

    <form action="user/fileUpload1" method="post" enctype="multipart/form-data">

        选择文件: <input type="file" name="upload"><br>
        <input type="submit" value="上传">

    </form>
    <br><br>

    <h3>spring mvc 文件上传</h3>
    <a href="user/fileUpload1">test</a>

    <form action="user/fileUpload2" method="post" enctype="multipart/form-data">

        选择文件: <input type="file" name="upload"><br>
        <input type="submit" value="上传">

    </form>
    <br><br>

    <h3>spring mvc 跨服务器文件上传</h3>
    <a href="user/fileUpload1">test</a>

    <form action="user/fileUpload3" method="post" enctype="multipart/form-data">

        选择文件: <input type="file" name="upload"><br>
        <input type="submit" value="上传">

    </form>
</body>
</html>
