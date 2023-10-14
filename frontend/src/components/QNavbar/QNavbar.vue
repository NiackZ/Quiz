<template>
  <v-app-bar fixed elevate-on-scroll scroll-target="#scrolling-techniques-7">
    <v-container class="d-flex align-center">
      <v-toolbar-title class="dp__pointer">
        <router-link to="/"
                     style="text-decoration: none; color: inherit;"
        >Home</router-link>
      </v-toolbar-title>
      <v-text-field
          class="w-100 pa-2"
          type="text"
          :loading="false"
          density="compact"
          variant="outlined"
          label="Поиск"
          append-inner-icon="mdi-magnify"
          single-line
          hide-details
          clearable
          @click:append-inner="onClick"
      />
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
      <v-dialog v-if="!isAuth" v-model="dialog" max-width="500">
        <v-card>
          <QLogin/>
        </v-card>
      </v-dialog>
    </v-container>
  </v-app-bar>

</template>

<script>
import { useTheme } from "vuetify";
import axios from '/src/axios/http-common'
import {mapActions, mapMutations, mapState} from "vuex";
import QNavbarUserAvatar from "./QNavbarUserAvatar.vue";
import QLogin from "../../pages/Public/Login.vue";

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
      dialog: false,
      loaded: false,
      loading: true,
      currentTheme: 1
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
  async mounted() {
    // try{
    //   const response = await axios.get('/users');
    //   console.log('OK', response.data);
    // }
    // catch (error) {
    //   console.log('ERROR', error);
    // }
  },
  methods: {
    ...mapActions('auth', ['enter']),
    ...mapMutations(['auth/setErrorState']),
    async registration() {
      try{
        const data = {
          username: this.registrationForm.username,
          email: this.registrationForm.email,
          password: this.registrationForm.password
        }
        console.log(data);
        const response = await axios.post('/auth/registration', data);
        console.log('OK', response.data);
      } catch (error) {
        const response = error.response.data;
        console.log('ERROR', response);
      }
    },
    onClick () {
      this.loading = true
      setTimeout(() => {
        this.loading = false
        this.loaded = true
      }, 2000)
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