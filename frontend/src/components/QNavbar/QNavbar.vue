<template>
  <v-app-bar fixed elevate-on-scroll scroll-target="#scrolling-techniques-7">
    <v-container class="d-flex align-center">
      <v-toolbar-title>Title</v-toolbar-title>
      <v-text-field
          class="w-100"
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
    </v-container>
  </v-app-bar>

</template>

<script>
import router from "../../router/router";
import { useTheme } from "vuetify";

export default {
  name: 'QNavbar',
  setup () {
    const theme = useTheme()
    return {
      theme,
      toggleTheme: () => theme.global.name.value = theme.global.current.value.dark ? 'light' : 'dark'
    }
  },
  data() {
    return {
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
  methods: {
    router() {
      return router
    },
    onClick () {
      this.loading = true

      setTimeout(() => {
        this.loading = false
        this.loaded = true
      }, 2000)
    }
  }
}
</script>

<style scoped>
.v-toolbar-title{
  min-width: auto;
  padding-right: 10px;
}
</style>