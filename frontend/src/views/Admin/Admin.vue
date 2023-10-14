<template>
  <h1 class="text-3xl font-bold underline">
    Hello world!
  </h1>
  <v-btn @click="goto">New title</v-btn>
  <router-view></router-view>
</template>

<script>
import router from "../../router/router.js";

export default {
  data() {
    return {
      showMenu: false,
      routes: [
        {
          path: '/admin/create/title',
          title: 'New title'
        }
      ]
    }
  },
  methods: {
    goto() {
      router.push({name: 'createTitle'})
    }
  }
}
</script>

<style scoped>

</style>