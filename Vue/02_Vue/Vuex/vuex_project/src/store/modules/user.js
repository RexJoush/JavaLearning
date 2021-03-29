/*
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 19:35:11
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 20:16:14
 */

// 数据
const state = {
    tudos: [],
}

// actions
const actions = {
    addTodo ({ commit }, todo) {
        // 提交一个 mutation 请求
        commit("ADD_TODO", {todo});
    },
}

// mutations
const mutations = {
    ADD_TODO: (state, {todo}) => {
        // 加入数组最前面
        state.tudos.unshift(todo);
    }
}

export default {
    state,
    actions,
    mutations,
}