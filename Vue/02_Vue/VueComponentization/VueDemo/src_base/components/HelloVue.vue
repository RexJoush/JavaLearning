<template>
  <div>
    <p>{{ msg }}</p>
  </div>
</template>

<script>
// 向外默认暴露一个配置对象（与 Vue 一致）
export default {
  name: "HelloVue",
  data() { // 在 vue 中，可以使用 data: {} 或者写函数，但在组件中必须写函数
    return {
      msg: 'Hello Vue',
    }
  }
}
</script>

<style scoped>
.msg {
  color: red;
  font-size: 30px;
}
</style>
