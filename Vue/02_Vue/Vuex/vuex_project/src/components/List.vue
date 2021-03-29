<!--
 * @Descripttion: your project
 * @version: 1.0
 * @Author: Rex Joush
 * @Date: 2021-03-21 20:21:58
 * @LastEditors: Rex Joush
 * @LastEditTime: 2021-03-21 20:49:32
-->

<template>
    <ul class="todo-main">
        <Item v-for="(todo, index) in todos" :key="index"
        :todo="todo" :deleteTodo="deleteTodo" :index="index"></Item>    
    </ul>
</template>


<script>

    import Item from "./Item.vue"

    export default {
        components: {
            Item,
        }
    }
</script>  
<style scoped>
    .todo-main {
        margin-left: 0px;
        border: 1px solid #ddd;
        border-radius: 2px;
        padding: 0px;
    }
</style>
