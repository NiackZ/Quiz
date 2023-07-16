<template>
  <v-app>
    <q-navbar/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import {defineComponent} from "vue";
import QNavbar from "./components/QNavbar/QNavbar.vue";
import {TOKEN} from "./constants/constants.js";

export default defineComponent({
  components: {QNavbar},
  created() {
    const token = localStorage.getItem(TOKEN)
    if (token) {
      this.$store.commit('auth/validateToken', {token});
    }
  }
})
</script>

<style>

</style>