<template>
  <h1 class="text-3xl font-bold underline">
    Hello world!
  </h1>
  <v-btn @click="goto">New title</v-btn>
  <v-btn @click="animeRoute">Anime</v-btn>
<!--  <router-view></router-view>-->
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
    },
    animeRoute() {
      router.push({name: 'anime'});
    }
  }
}
</script>

<style scoped>

</style>