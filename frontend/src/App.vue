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
import {mapState} from "vuex";

export default defineComponent({
  components: {QNavbar},
  computed: {
    ...mapState({
      isAuth: state => state.auth.isAuth,
      loading: state => state.auth.loading
    })
  },
  created() {
    if (!this.isAuth && localStorage.getItem(ACCESS_TOKEN)) {
      this.$store.dispatch('auth/validateToken');
    }
  }
})
</script>

<style>

</style>