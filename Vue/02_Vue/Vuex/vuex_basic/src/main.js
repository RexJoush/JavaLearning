/*
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 14:33:15
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 16:54:08
 */
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store, // 所有组件对象都多了一个属性 $store，相当于是个 store 对象
  components: { App },
  template: '<App/>'
})
