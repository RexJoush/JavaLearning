<template>
  <div>
    <header class="site-header jumbotron">
      <div class="container">
        <div class="row">
          <div class="col-xs-12">
            <h1>请发表对 Vue 的评论</h1>
          </div>
        </div>
      </div>
    </header>
    <div class="container">
      <!-- 将 addComment 方法传递给子组件 -->
      <Add :addComment="addComment"/>
      <!-- 组件间通信，一般是同名属性传递 -->
      <List :comments="comments" :deleteComment="deleteComment"/>

    </div>
  </div>
</template>

<script>
import Add from './components/Add'
import List from "./components/List";


export default {

  data: () => {
    return {
      comments: [ // 数据在哪个组件，操作或者更新数据的行为（方法）就应该定义在哪个组件
        {
          name: 'Bob',
          content: 'Hello Vue',
        },
        {
          name: 'Kite',
          content: '你好 Vue',
        },
        {
          name: 'Alice',
          content: 'Vue',
        },

      ]
    }
  },

  methods: {
    // 添加评论
    addComment: function (comment) {
      // 在列表前面添加用 unshift，在后面用 push
      this.comments.unshift(comment);
    },

    // 删除指定下标的评论
    deleteComment: function (index) {
      this.comments.splice(index, 1);
    }
  },

  components: {
    Add,
    List
  },
  name: "App",
}
</script>

<style scoped>

</style>
