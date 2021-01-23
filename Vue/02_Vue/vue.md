# Vue 组件化编码

## 使用 vue-cli 创建模板项目

#### 说明
* vue-cli 是vue 官方提供的脚手架工具
* github: <https://github.com/vuejs/vue-cli>
* 作用: 从 <https://github.com/vuejs-templates> 下载模板项目

#### 创建 vue 项目
```shell
# 全局安装 vue 脚手架工具
npm install -g vue-cli

# 初始化 vue 项目，使用 webpack 模板
vue init webpack vue_demo

# 安装依赖
cd vue_demo
npm i

# 运行项目
npm run dev
```
#### 模板项目结构
```text
|-- build : webpack 相关的配置文件夹(基本不需要修改)
    |-- dev-server.js : 通过express 启动后台服务器
|-- config: webpack 相关的配置文件夹(基本不需要修改)
    |-- index.js: 指定的后台服务的端口号和静态资源文件夹
|-- node_modules
|-- src : 源码文件夹
    |-- components: vue 组件及其相关资源文件夹
    |-- App.vue: 应用根主组件
    |-- main.js: 应用入口js
|-- static: 静态资源文件夹
|-- .babelrc: babel 的配置文件
|-- .eslintignore: eslint 检查忽略的配置
|-- .eslintrc.js: eslint 检查的配置
|-- .gitignore: git 版本管制忽略的配置
|-- index.html: 主页面文件
|-- package.json: 应用包配置文件
|-- README.md: 应用描述说明的readme 文件
```

## 项目的打包与发布

#### 打包
```shell
npm run build
```
#### 发布 1，使用静态服务器
* 下载 serve 包
```shell
npm install -g serve
serve dist
```
* 访问 <http://localhost:5000>

#### 发布 2，使用动态服务器 Tomcat
* 修改配置 `webpack.prod.conf.js`
```javascript
output:{
  publicPath: '/xxx' // 打包文件夹的名称
}
```
* 重新打包
* 修改 dist 目录为项目名称
* 放到 tomcat 下

## ESLint

#### 说明
* ESLint 是一个代码规范检查工具
* 它定义了很多特定的规则, 一旦你的代码违背了某一规则, eslint 会作出非常有用的提示
* 官网: <http://eslint.org/>
* 基本已替代以前的 JSLint

#### ESLint 提供以下支持
* ES
* JSX
* style 检查
* 自定义错误和提示

#### ESLint 提供以下支持
* 语法错误校验
* 不重要或丢失的标点符号，如分号
* 没法运行到的代码块（使用过WebStorm 的童鞋应该了解）
* 未被使用的参数提醒
* 确保样式的统一规则，如sass 或者less
* 检查变量的命名

#### ESLint 规则的错误等级有三种
* 0：关闭规则。
* 1：打开规则，并且作为一个警告（信息打印黄色字体）
* 2：打开规则，并且作为一个错误（信息打印红色字体）

#### 相关配置文件
* .eslintrc.js : 全局规则配置文件
    ```javascript
    'rules': {
        'no-new': 1
    }
    ```
* 在js/vue 文件中修改局部规则
    ```javascript
    /* eslint-disable no-new */
    new Vue({
        el: 'body',
        components: { App }
    })
    ```
* .eslintignore: 指令检查忽略的文件
   - .js
   - .vue