<template>
  <v-app-bar fixed elevate-on-scroll scroll-target="#scrolling-techniques-7">
    <v-container class="d-flex align-center">
      <v-toolbar-title>Title</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>

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
    }
  }
}
</script>

<style scoped>
.navbar {
  height: 50px;
  background: deepskyblue;
  box-shadow: 2px 2px 4px gray;
  display: flex;
  align-items: center;
  padding: 15px;
}
.navbar_bnts{
  margin-left: auto;
}
</style>