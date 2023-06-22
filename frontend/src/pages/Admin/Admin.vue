<template>
  <v-navbar ref="userNavbar" :routes="routes"/>
  <h1 class="text-3xl font-bold underline">
    Hello world!
  </h1>
</template>

<script>
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
  }
}
</script>

<style scoped>

</style>