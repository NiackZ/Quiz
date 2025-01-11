<template>
  <v-app>
    <v-overlay v-model="overlayLoading"
               scrim="black"
               class="align-center justify-center">
      <v-progress-circular
          color="primary"
          size="64"
          indeterminate
      ></v-progress-circular>
    </v-overlay>
    <q-navbar/>
    <v-main>
      <v-container>
        <router-view/>
      </v-container>
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
      isAuth: (state) => state.auth.isAuth,
      authLoading: (state) => state.auth.authLoading
    }),
    ...mapState("overlay", {
      overlayLoading: (state) => state.overlayLoading
    }),
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