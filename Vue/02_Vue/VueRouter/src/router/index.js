import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

import Home from '../views/Home.vue'
import About from '../views/About.vue'

Vue.use(Router)

// 向外暴露一个 VueRouter 对象
export default new Router({
  // 配置对象，n 个路由
  routes: [
    {
      path: '/about',
      component: About
    },
    {
      path: '/home',
      component: Home
    }
  ]
})
