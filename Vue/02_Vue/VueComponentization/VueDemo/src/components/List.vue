<template>
  <div class="col-md-8">
    <h3 class="reply">评论回复：</h3>
    <h2 v-show="comments.length === 0">暂无评论，点击左侧添加评论！！！</h2>
    <ul class="list-group">
      <Item v-for="(comment, index) in comments" :key="index" :comment="comment" :deleteComment="deleteComment" :index="index"/>
    </ul>
  </div>
</template>

<script>
import Item from "./Item";

export default {

  // 声明接受属性：这个属性就可以成为组件对象的属性，就可以在组件对象中读取
  props: ['comments', 'deleteComment'],
  components: {
    Item
  },
  name: "List"
}
</script>

<style scoped>
.reply {
  margin-top: 0px;
}

</style>
