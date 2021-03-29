<!--
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 14:33:15
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 16:44:23
-->
# vuex_demo

> A Vue.js project

## Vuex

#### vuex 是什么
* 对 vue 应用中多个组件的共享状态进行集中式管理（读/写）

#### 状态自管理应用
* state 驱动应用的数据源
* view 以声明方式将 state 映射到视图
* actions 响应在 view 上的用户输入导致的状态变化（包含n个更新状态的方法）

#### 多组件共享状态
* 多个视图依赖于同一状态
* 来自不同视图的行为需要变更同一状态
* 以前的解决办法
    - 将数据以及操作数据的行为都定义在父组件
    - 将数据以及操作数据的行为传递给需要的各个组件，有可能需要多级传递
* vuex 就使来解决这个问题的

## Vuex 核心概念和 Api

#### State
* vue 的管理状态对象
* 应该是唯一的
```javascript
const state = {
    xxx: initValue
}
```

#### mutations
* 包含多个事件回调函数的对象
* 通过执行: commit() 来触发 mutation 的调用, 间接更新 state
* 谁来触发: 组件中: $store.dispatch('action 名称', data1) // 'zzz'
* 可以包含异步代码(定时器, ajax)
```javascript
const actions = {
    zzz ({commit, state}, data1) {
        commit('yyy', {data1})
    }
}
```

#### getters
* 包含多个计算属性的对象
* 谁来读取: 组件中: $store.getters.xxx
```javascript
const getters = {
    mmm (state) {
        return ...
    }
}
```

#### modules
* 包含多个 module
* 向外暴露 store 对象
```javascript
export default new Vuex.Store({
    state,
    mutations,
    actions,
    getters
})
```

#### 组件中
```javascript
import {mapState, mapGetters, mapActions} from 'vuex'
export default {
    computed: {
    ...mapState(['xxx']),
    ...mapGetters(['mmm']),
    }
    methods: mapActions(['zzz'])
}
{{xxx}} {{mmm}} @click="zzz(data)"
```

#### 映射store
```javascript
import store from './store'
new Vue({
    store
})
```

#### store 对象
* 所有用 vuex 管理的组件中都多了一个属性 $store, 它就是一个 store 对象
* 属性
    - state: 注册的 state 对象
    - getters: 注册的 getters对象
* 方法
    `dispatch(actionName, data); // 分发调用 action`


``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
