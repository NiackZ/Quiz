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
          <v-tabs
              v-model="tabs"
              color="primary"
              align-tabs="center"
          >
            <v-tab value="1">Вход</v-tab>
            <v-tab value="2">Регистрация</v-tab>
          </v-tabs>
          <v-window v-model="tabs" class="pa-4">
            <v-window-item value="1">
              <v-form v-if="!loginForm.forgotPass">
                <v-text-field
                    v-model="loginForm.username"
                    label="Имя пользователя"
                    variant="outlined"
                    placeholder="Введите имя пользователя"
                    prepend-inner-icon="mdi-account"
                />
                <v-text-field
                    v-model="loginForm.password"
                    label="Пароль"
                    variant="outlined"
                    hide-details
                    prepend-inner-icon="mdi-lock-outline"
                />
                <div>
                  <v-checkbox
                      class="q-checkbox"
                      v-model="loginForm.rememberMe"
                      label="Запомнить меня"
                      color="primary"
                      hide-details
                      density="comfortable"
                  />
                  <div class="text-center forgot-pass mb-2">
                    <span @click="loginForm.forgotPass = true">Забыл пароль</span>
                  </div>
                </div>
                <v-btn variant="tonal"
                       color="primary"
                       block
                       @click="enter(this.loginForm)"
                >
                  Войти
                </v-btn>
              </v-form>
              <v-form v-else>
                <v-text-field
                    v-model="loginForm.email"
                    label="Email"
                    variant="outlined"
                    type="email"
                    placeholder="Введите email"
                    prepend-inner-icon="mdi-email-outline"
                />
                <div class="text-center forgot-pass mb-2">
                  <span @click="loginForm.forgotPass = false">Вспомнил пароль</span>
                </div>
                <v-btn variant="tonal"
                       color="primary"
                       block
                >
                  Восстановить
                </v-btn>
              </v-form>
            </v-window-item>
            <v-window-item value="2">
              <v-form>
                <v-text-field
                    v-model="registrationForm.username"
                    label="Имя пользователя"
                    variant="outlined"
                    placeholder="Введите имя пользователя"
                    prepend-inner-icon="mdi-account"
                />
                <v-text-field
                    v-model="registrationForm.email"
                    label="Email"
                    variant="outlined"
                    type="email"
                    placeholder="Введите email"
                    prepend-inner-icon="mdi-email-outline"
                />
                <v-text-field
                    v-model="registrationForm.password"
                    label="Пароль"
                    variant="outlined"
                    type="password"
                    prepend-inner-icon="mdi-lock-outline"
                />
                <v-text-field
                    v-model="registrationForm.confirmPassword"
                    label="Повторите пароль"
                    variant="outlined"
                    type="password"
                    prepend-inner-icon="mdi-lock-outline"
                />
                <v-btn variant="tonal"
                       color="primary"
                       block
                       @click="registration"
                >
                  Зарегистрироваться
                </v-btn>
              </v-form>
            </v-window-item>
          </v-window>
        </v-card>
      </v-dialog>
    </v-container>
  </v-app-bar>

</template>

<script>
import { useTheme } from "vuetify";
import axios from '/src/axios/http-common'
import {mapActions, mapState} from "vuex";
import QNavbarUserAvatar from "./QNavbarUserAvatar.vue";

export default {
  name: 'QNavbar',
  components: {QNavbarUserAvatar},
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
      tabs: null,
      dialog: false,
      loaded: false,
      loading: !false,
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
      isAuth: state => state.auth.isAuth
    })
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