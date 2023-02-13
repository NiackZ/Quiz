<template>
  <form @submit.prevent>
    <h3>Создание поста</h3>
    <my-input v-model="post.title" placeholder="Заголовок" />
    <my-input v-model="post.body" placeholder="Описание" />
    <my-button class="btn form_btn" @click="createPost">Добавить пост</my-button>
  </form>
</template>

<script>

export default {
  data(){
    return {
      post: {
        id: Date.now(),
        title: "",
        body: ""
      }
    }
  },
  methods:{
    createPost() {
      this.$emit('create', this.post)
      this.post = {
        id: Date.now(),
        title: "",
        body: ""
      }
    }
  }
}
</script>

<style scoped>
.form_btn {
  align-self: flex-end;
  margin-top: 10px;
  cursor: pointer;
}
</style>