## Vue.js 简单入门

#### Vue.js 概述与快速入门

* Vue.js 介绍
```text
Vue.js是一个构建数据驱动的 web 界面的渐进式框架。Vue.js 的目标是通过尽可能简单的 API 实现响应的数据绑
定和组合的视图组件。它不仅易于上手，还便于与第三方库或既有项目整合。
```
* MVVM 模式
```text
MVVM 是 Model-View-ViewModel 的简写。它本质上就是 MVC 的改进版。MVVM 就是将其中的 View 的状态和行为
抽象化，让我们将视图 UI 和业务逻辑分开
MVVM 模式和 MVC 模式一样，主要目的是分离视图（View）和模型（Model）
Vue.js 是一个提供了 MVVM 风格的双向数据绑定的 Javascript 库，专注于 View 层。它的核心是 MVVM 中的 VM，
也就是 ViewModel。ViewModel负责连接 View 和 Model，保证视图和数据的一致性，这种轻量级的架构让前端
开发更加高效、便捷
```
* Vue.js 快速入门
    - jQuery 的异步请求
    ```javascript
    // 逻辑部分
    /* ======================================== */
    $.ajax({
        url: "springmvc/testAjax",
        type: "post",
        data: {
            id: 1,
            username: "username"
        },
        dataType: "json",
        success: (data) => {
            // 赋值部分
            /* ------------------------------ */
            // 使用 jQuery 给页面赋值
            $('#username').html(data.username);
            /* ------------------------------ */
        }
    })
    /* ======================================== */
    ```
    - Vue 解决的问题就是将逻辑部分和赋值部分进行分离
    - 快速入门
    ```html
    <!--  webapp.demo01.html  -->
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="utf-8" />
      <title>快速入门</title>
      <script src="js/vuejs-2.5.16.js"></script>
    </head>
    <body>
        <div id="app">
            {{message}} <!--Vue 的插值表达式，将 data 中定义的数据显示到此处-->
        </div>
        <script>
            new Vue({
                el:'#app', //表示当前vue对象接管了div区域
                data:{
                message:'hello world' //注意不要写分号结尾
                }
            });
        </script>
    </body>
    </html>
    ```
* 插值表达式
    - 数据绑定最常见的形式就是使用 “Mustache” 语法 (双大括号) 的文本插值
    - Mustache 标签将会被替代为对应数据对象上属性的值。 
    - 无论何时，绑定的数据对象上属性发生了改变，插值处的内容都会更新。
    ```javascript
    // Vue.js 都提供了完全的 JavaScript 表达式支持
    {{ number + 1 }}
    {{ ok? 'YES' : 'NO' }}
  
    /* 
        这些表达式会在所属 Vue 实例的数据作用域下作为 JavaScript 被解析。
        有个限制就是，每个绑定都只能包含单个
        表达式，所以下面的例子都不会生效。
    */
  
    // 这是语句，不是表达式
    {{ var a = 1 }}
    // 流控制也不会生效，请使用三元表达式
    {{ if (ok) { return message } }}
    ```

#### Vue.js 常用系统指令
* v-on 
    - 可以用 `v-on` 指令监听 DOM 事件，并在触发时执行一些 JavaScript 代码
    - v-on:click 点击事件 `demo02.html`
    - v-on:keydown 键盘按下 `demo03.html`
    - v-on:mouseover 鼠标移动到 `demo04.html`
    - v-on:事件修饰符 `demo05.html`
        ```html
            <div id="app">
            <!--  阻值 submit 事件的发生，.stop 等同于 event.stopPropagation(); 即停止事件传播  -->  
            <form action="https://www.baidu.com" method="post" @submit.prevent>
                <input type="submit" value="提交">
            </form>
        </div>
        ```
    - v-on 按键修饰符 `demo06.html`
        ```text
        vue 支持的按键修饰符
        .enter 
        .tab
        .delete (捕获 删除(del) 和 退格(backspace))
        .esc
        .space
        .up
        .down
        .left
        .right
        .ctrl
        .alt
        .shift
        .meta
        ```
    - v-text 与 v-html `demo07.html`
    - v-bind `demo08.html`
    - v-model `demo09.html`