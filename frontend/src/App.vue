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
        <auth-required v-if="requiresAuth"/>
        <router-view v-else/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import {defineComponent} from "vue";
import QNavbar from "./components/QNavbar/QNavbar.vue";
import {ACCESS_TOKEN} from "./constants/constants.js";
import {mapState} from "vuex";
import AuthRequired from "./views/Error/AuthRequired.vue";

export default defineComponent({
  components: {AuthRequired, QNavbar},
  computed: {
    ...mapState({
      isAuth: (state) => state.auth.isAuth,
      authLoading: (state) => state.auth.authLoading
    }),
    ...mapState("overlay", {
      overlayLoading: (state) => state.overlayLoading
    }),
    requiresAuth() {
      const currentRoute = this.$route;
      return currentRoute.meta.requiresAuth && !this.isAuth;
    }
  },
  created() {
    if (!this.isAuth && localStorage.getItem(ACCESS_TOKEN)) {
      this.$store.dispatch('auth/validateToken');
    }
  },
  watch: {
    isAuth(newVal) {
      // Если пользователь авторизовался, возвращаем его на сохранённый маршрут
      if (newVal && this.authRequiredRoute) {
        this.$router.push(this.authRequiredRoute);
        this.$store.commit('auth/clearAuthRequiredRoute');
      }
    }
  }
})
</script>

<style>

</style>