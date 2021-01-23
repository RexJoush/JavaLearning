// 程序的主入口文件

// 引入 createApp 函数，创建对应的应用，产生应用的实例对象
import { createApp } from 'vue'

// 引入 app 组件（所有组件的父级组件）
import App from './App.vue'

// 创建 app 应用，返回对应的实例对象，调用 mount 方法进行挂载
createApp(App).mount('#app')
