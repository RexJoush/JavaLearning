<!--
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 14:33:15
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 19:08:06
-->
<template>
  <div id="app">
    <div>
      <p>click {{ count }} times, count is {{evenOrOdd}}</p>
      <button @click="increment">+</button>
      <button @click="decrement">-</button>
      <button @click="incrementIfOdd">increment if odd</button>
      <button @click="incrementAsync">increment async</button>
    </div>
  </div>
</template>

<script>

import {mapState, mapActions, mapGetters} from 'vuex'

export default {
  name: 'App',

  computed: {

    ...mapState(['count']),
    ...mapGetters(['evenOrOdd']),
    // 如果事件的处理名字和 actions 的名字不一样的话，不用数组，用对象
    
    // 前面是事件名字，后面是 actions 名字
    // ...mapGetters({evenOrOdd :'evenOrOdd2'}),

    // evenOrOdd() {
    //   return this.$store.getters.evenOrOdd
    // }
  },

  methods: {

    ...mapActions(['increment','decrement','incrementIfOdd','incrementAsync']),
    // increment: function () {
    //   // 增加，通知 vuex 更新
    //   this.$store.dispatch("increment"); // 出发 store 中对应的 action 调用
    // },
    // decrement: function () {
    //   // 通知 vuex 减少
    //   this.$store.dispatch("decrement");
    // },
    // // 奇数加一
    // incrementIfOdd: function () {
    //   this.$store.dispatch("incrementIfOdd");
    // },
    // // 过一秒增加
    // incrementAsync: function () {
    //   this.$store.dispatch("incrementAsync");
    // },
  }

}
</script>

<style>

</style>
