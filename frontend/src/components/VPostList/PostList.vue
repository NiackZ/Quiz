<template>
  <transition-group name="post-list"
                    v-if="posts.length > 0">
    <post-item v-for="post in posts"
               :post="post"
               :key="post.id"
               @removePost="$emit('removePost', post)"
    />
  </transition-group>
  <h2 v-else>
    Посты не найдены.
  </h2>
</template>

<script>
import PostItem from "@/components/VPostItem/PostItem.vue";

export default {
  components: {PostItem},
  props: {
    posts: {
      type: Array,
      required: true,
      default: []
    }
  }
}
</script>

<style scoped>
  .post-list-item {
    display: inline-block;
    margin-right: 10px;
  }
  .post-list-enter-active,
  .post-list-leave-active {
    transition: all 0.2s ease;
  }
  .post-list-enter-from,
  .post-list-leave-to {
    opacity: 0;
    transform: translateX(30px);
  }
  .post-list-move {
    transition: transform 0.4s ease;
  }
</style>