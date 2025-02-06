<template>
  <v-app-bar fixed elevate-on-scroll scroll-target="#scrolling-techniques-7">
    <v-container class="d-flex align-center justify-center">
      <v-toolbar-title class="dp__pointer d-content">
        <router-link to="/"
                     style="text-decoration: none; color: inherit; flex: 0 1 auto;"
                     v-text="getSiteName"
        ></router-link>
      </v-toolbar-title>
      <v-text-field
          class="pa-2"
          type="text"
          v-model="search.text"
          :loading="search.loading"
          density="compact"
          variant="outlined"
          label="Поиск"
          append-inner-icon="mdi-magnify"
          single-line
          hide-details
          clearable
          @update:model-value="handleInput"
          @click:append-inner="searchData"
      >
        <template v-slot:loader v-if="search.loading">
          <v-progress-linear height="4"
                             indeterminate
                             color="dark"
          ></v-progress-linear>
        </template>
      </v-text-field>
      <v-btn icon @click="toggleTheme">
        <v-icon>{{theme.current.value.dark ? 'mdi-weather-night' : 'mdi-weather-sunny'}}</v-icon>
        <v-tooltip
            activator="parent"
            location="bottom"
        >Сменить тему</v-tooltip>
      </v-btn>
      <v-btn v-if="!isAuth" @click="dialog = true">
        Войти
      </v-btn>
      <template v-else>
        <QNavbarUserAvatar/>
      </template>
      <v-dialog v-if="!isAuth" v-model="dialog" max-width="500" scrim="black">
        <v-card>
          <QLogin/>
        </v-card>
      </v-dialog>
    </v-container>
  </v-app-bar>
</template>

<script>
import { useTheme } from "vuetify";
import {mapActions, mapMutations, mapState} from "vuex";
import QNavbarUserAvatar from "./QNavbarUserAvatar.vue";
import QLogin from "./QLogin.vue";
import {SITE_NAME} from "../../constants/constants.js";

export default {
  name: 'QNavbar',
  components: {QLogin, QNavbarUserAvatar},
  setup () {
    const theme = useTheme()
    return {
      theme,
      toggleTheme: () => theme.global.name.value = theme.global.current.value.dark ? 'light' : 'dark'
    }
  },
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        forgotPass: false,
        email: ""
      },
      registrationForm: {
        username: "",
        email: "",
        password: "",
        confirmPassword: ""
      },
      search: {
        loading: false,
        text: null
      },
      dialog: false,
      authLoading: true,
      currentTheme: 1,
      timeout: null
    }
  },
  props: {
    routes: {
      type: Array,
      default: () => {
        return [
          {
            path: "",
            title: ""
          }
        ]
      },
      required: false
    }
  },
  watch:{
    dialog: function(newVal, oldVal) {
      if (!oldVal) this.clearModal();
    },
    isAuth: function(newVal, oldVal) {
      if (newVal) {
        this.dialog = false;
        this.clearModal();
      }
    }
  },
  computed: {
    getSiteName() {
      return SITE_NAME;
    },
    ...mapState({
      isAuth: state => state.auth.isAuth,
      error: state => state.auth.error
    }),
    calcClasses() {
      console.log(this.error);
      if (this.error != null) {
        return "mb-4 v-field--error";
      }
      else return "mb-4";
    }
  },
  methods: {
    ...mapActions('auth', ['enter']),
    ...mapMutations(['auth/setErrorState']),
    ...mapActions('search', ['searchAnime']),
    async searchData() {
      try {
        this.search.loading = true;
        await this.searchAnime(this.search.text)
      }
      catch (e) {
        console.error(e);
      }
      this.search.loading = false;
    },
    handleInput() {
      clearTimeout(this.timeout);
      this.timeout = setTimeout(this.searchData, 500);
    },
    clearModal() {
      this.loginForm.username = '';
      this.loginForm.email = '';
      this.loginForm.password = '';
      this.loginForm.rememberMe = false;
      this.loginForm.forgotPass = false;
      this["auth/setErrorState"](null);
    }
  }
}
</script>

<style scoped>
div.forgot-pass span {
  cursor: pointer;
  transition: .3s;
}
div.forgot-pass span:hover {
  color: rgb(var(--v-theme-primary)) !important;
}
.v-toolbar-title {
  min-width: auto;
  padding-right: 10px;
}
</style>