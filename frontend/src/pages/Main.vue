<template>
  <v-navbar ref="userNavbar" :routes="routes"/>
  <h1>Welcome, traveler!</h1>
  <router-link to="/admin">Admin</router-link>
</template>

<script>
export default {
  data() {
    return {
      routes: [
        {
          path: '/admin',
          title: 'Adminka'
        }
      ]
    }
  }
}
</script>

<style scoped>
*{

}
</style>