<!--
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 20:04:16
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 20:18:57
-->
<template>
    <div class="todo-header">
        <input type="text" placeholder="请输入任务名称，回车确认"
        v-model="inputTodo" @keyup.enter="add">
    </div>
</template>

<script>
export default {
    data() {
        return {
            inputTodo: '', // 组件内部使用的状态，不存在共享问题
        }
    },
    methods: {
        add () {
            const inputTodo = this.inputTodo.trim();
            if (!inputTodo) {
                return;
            }

            // 封装 Todo 对象
            const todo = {
                title: inputTodo,
                complete: false,
            }

            // 添加到 Todo 列表
            this.$store.dispatch('addTodo', todo);
            
            // 清除输入
            this.inputTodo = ''
        }
    }
}
</script>

<style scoped>
.todo-header input{
    width: 560px;
    height: 28px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 4px 7px;
}
</style>