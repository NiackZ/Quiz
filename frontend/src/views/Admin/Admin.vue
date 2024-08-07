<template>
  <v-row justify="center">
    <v-col v-for="block in blocks"
           :cols="computedColumnCount"
    >
      <v-card v-ripple.center>
        <router-link :to="{ name: block.link }" class="text-decoration-none">
          <v-card-text class="text-center custom-card-text">
            {{block.name}}
          </v-card-text>
        </router-link>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>

import {ADMIN_ROUTE} from "../../router/routeConstants.js";

export default {
  data() {
    return {
      blocks: [
        { name: "Аниме", link: ADMIN_ROUTE.ANIME },
        { name: "Жанры", link: ADMIN_ROUTE.GENRE },
        { name: "Типы", link: null },
        { name: "Студии", link: null },
        { name: "Статусы", link: null }
      ]
    }
  },
  async created() {

  },
  computed: {
    computedColumnCount() {
      if (this.$vuetify.display.lgAndUp) { // если экран большой
        return 2;
      }
      else if (this.$vuetify.display.md || this.$vuetify.display.sm) { // если экран средний
        return 3;
      }
      else if (this.$vuetify.display.xs) {
        return 12;
      }
    }
  }
}
</script>

<style scoped>
.custom-card-text {
  transition: 0.3s ease;
}

.custom-card-text:hover {
  background-color: #514080 !important; /* Измените этот цвет на нужный вам */
  color: white !important;
}
</style>

