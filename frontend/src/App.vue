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
import {ACCESS_TOKEN} from "./constants/constants.js";

export default defineComponent({
  components: {QNavbar},
  created() {
    if (localStorage.getItem(ACCESS_TOKEN)) {
      this.$store.dispatch('auth/validateToken');
    }
  }
})
</script>

<style>

</style>