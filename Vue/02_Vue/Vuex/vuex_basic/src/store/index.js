/*
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 16:44:33
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 17:19:43
 */

/*
    vuex 的核心管理对象模块 store
*/
import Vue from "vue"
import Vuex from 'vuex'

Vue.use(Vuex);

// 状态
const state = {
    count: 0,
};

// 包含多个更新 state 函数的对象
const mutations = {
    // 增加的 mutation
    INCREMENT (state) {
        state.count++;
    },
    // 减少的 mutation
    DECREMENT (state) {
        state.count--;
    }
};

// 对应事件回调函数对象
const actions = {
    // 增加的 action
    increment({ commit }) {
        // 提交增加的 mutation，等于 此 commit 最终导致一个 mutation 被调用了
        commit("INCREMENT","1");
    },
    // 减少的 action
    decrement({ commit }) {
        // 提交减少的 mutation，等于 此 commit 最终导致一个 mutation 被调用了
        commit("DECREMENT","1");
    },
    // 条件的 action
    incrementIfOdd({ commit, state },) {
        // 提交减少的 mutation，等于 此 commit 最终导致一个 mutation 被调用了
        if (state.count % 2 === 1){
            commit("INCREMENT");
        }
    },
    // 异步的 action
    incrementAsync({ commit }) {
        // 提交减少的 mutation，等于 此 commit 最终导致一个 mutation 被调用了
        setTimeout(()=>{
            commit("INCREMENT");
        },1000)
    },

};

// 包含多个 getter 计算属性的函数对象
const getters = {
    evenOrOdd(state) {
        return state.count % 2 === 0 ? "偶数" : "奇数"
    }
};

export default new Vuex.Store({
    state,
    mutations,
    actions,
    getters,
})