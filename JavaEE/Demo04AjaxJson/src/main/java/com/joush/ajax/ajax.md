## Ajax

#### 概念，ASynchronous JavaScript And XML
* 异步和同步
#### 实现方式
* 原生的JS实现方式（了解）
* JQuery实现方式
    - $.ajax()
        - 语法 $.ajax(url, [settings]);
        ``` javascript
        $.ajax({
            url: '/Demo04/jqueryServlet', // 请求参数
            type: 'GET', // 请求方式
            data: { // 发送的数据
                "username": 'aaa',
                "password": 'bbb'
            },
            dataType: 'json', // 响应的数据格式
            success: res => { // 成功结果
                console.log(res);
            },
            error: err => { // 错误结果
                console.log(err);
            }
        })
        ```
    - $.get(),$.post()
        - 语法 
        ``` javascript
        $.get(url, [data], [callback], [type]);
        $.post(url, [data], [callback], [type]);
        ```
            
        - 参数
            - url, 请求路径
            - data, 请求参数
            - callback, 回调函数
            - type, 响应结果类型

## JSON

#### 概念，JavaScript Object Notation  
* json多用于数据的存储
#### 语法
* 基本规则
    - 数据在键值对中
        - 键用引号引起来，也可不用
    - 数据由逗号分隔
    - 花括号保存对象
    - 方括号保存数组
* 获取数据
    - json对象.键名     obj.name
    - json对象["键名"]  obj["name""]
    - 数组对象[索引]     arr[1]
* 遍历json
    ``` javascript
    for(let key in obj){
        // 错误写法，因为key获取的是 字符串，即 obj."name"，所以拿不到值
        console.log(key + ":" + obj.key);
        // 正确写法，不用加 ""
        console.log(key + ":" + obj[key]);
    }        
    ```    
#### JSON数据和Java对象的转换
* 解析器
    常见的解析器： Jsonlib,Gson,fastjson,jackson
* JSON转换为Java对象
    - 导入jar包
        ``` xml
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.11.3</version>
        </dependency>
        
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.11.3</version>
        </dependency>
        
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.11.3</version>
        </dependency>
        ```
    - 创建 Jackson核心对象 ObjectMapper
    - 调用 ObjectMapper 对象的方法进行转换
        - readValue(json字符串,Class类型);
* Java对象转为JSON
    - 使用步骤
        - 导入jar包
        - 创建 Jackson核心对象 ObjectMapper
        - 调用 ObjectMapper 对象的方法进行转换
    - 注解
        - @JsonIgnore: 排除属性
        - @JsonFormat: 属性格式化
             `@JsonFormat(pattern = "yyyy-MM-dd")`
    - 复杂的java对象转换
        - List: 数组
        - Map: 对象格式一致