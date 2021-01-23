<template>
<!-- Vue2 中的 html 模板必须有一对跟标签，Vue3 组件的 html 模板可以没有根标签 -->
  <img alt="Vue logo" src="./assets/logo.png">
  <HelloWorld msg="Welcome to Your Vue.js App"/>
</template>

<script>
// 这里可以写 ts 代码

// defineComponent 函数，目的是定义一个组件，内部可以传入一个配置对象

// 引入一个子级组件
import HelloWorld from './components/HelloWorld.vue'

// 暴露出去一个定义好的组件 
export default {
  // 当前组件名字是 App
  name: 'App',
  // 注册组件
  components: {
    // 注册一个子级组件
    HelloWorld
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
