<template>
  <div>
    <img class="logo" src="assetsogo.png" alt="logo">
    <!--  3.使用组件标签  -->
    <HelloVue/>
  </div>
</template>

<script>
  // 1.引入 HelloVue 组件
  import HelloVue from './components/HelloVue'

  // 默认暴露一个配置对象
  export default {
    components: {HelloVue},
    // 2.映射组件标签
    comments: {
      HelloVue
    }
  }
</script>

<style>
  .logo {
    width: 200px;
    height: 200px;
  }
</style>

<!--<template>
  <div id="app">
    <img src="./assets/logo.png">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>-->
