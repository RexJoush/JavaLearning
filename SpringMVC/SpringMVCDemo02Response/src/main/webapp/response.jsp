<%--
  Created by IntelliJ IDEA.
  User: Joush
  Date: 2020/12/6
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Title</title>
    <script src="js/jquery.min.js"></script>
    <script>

        $(function(){
            $("#btn1").click(() => {
                // alert("hello button");
                // 发送 ajax 请求
                $.ajax({
                    url: 'user/testAjax',
                    contentType: 'application/json;charset=utf-8',
                    data: '{"username": "joush", "password": "1234", "age":23}',
                    dataType: "json",
                    method: 'post',
                    success: (data) => {
                        console.log(data);
                    }

                })
            })
        })

    </script>
</head>
<body>
    <a href="user/testString">testString</a>
    <br><br>

    <a href="user/testVoid">testVoid</a>
    <br><br>

    <a href="user/testModelAndView">testModelAndView</a>
    <br><br>

    <a href="user/testForwardOrRedirect">testForwardOrRedirect</a>
    <br><br>

    <button id="btn1">发送 ajax</button>
</body>
</html>
