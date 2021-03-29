/*
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 19:33:46
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 19:39:33
 */

import Vue from 'vue';
import Vuex from 'vuex';
import getters from './getters'
import user from './modules/user'

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        user,
    },
    getters
})

export default store;
