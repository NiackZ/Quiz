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

export default defineComponent({
  components: {QNavbar}
})
</script>

<style>

</style>