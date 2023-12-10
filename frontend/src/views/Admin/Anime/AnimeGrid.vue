<template>
  <v-row>
    <v-col v-for="(anime, index) in animes"
           :key="index"
           :cols="computedColumnCount"
           :style="{ height: this.cellHeight }"
    >
      <router-link :to="{ name: ANIME_DETAIL_ROUTE(), params: { id: anime.id } }" class="text-decoration-none">
        <div class="d-flex justify-center grid-cell bg-img-center h-100 "
             :style="{ width: cellWidth, 'background-image': `url(${anime.posterURL})` }"
        >
          <div class="short-description">
            {{anime.rusName}}
            <br/>
            {{anime.romName}}
          </div>
        </div>
        {{ anime.type.name }}
      </router-link>
    </v-col>
  </v-row>
</template>

<script>
import axios from "/src/axios/http-common.js";
import {ANIME_DETAIL_ROUTE} from "../../../router/routeConstants.js";

export default {
  methods: {
    ANIME_DETAIL_ROUTE() {
      return ANIME_DETAIL_ROUTE;
    }
  },
  data() {
    return {
      animes: [],
      cellWidth: 'auto',
      cellHeight: '500px'
    }
  },
  async created() {
    const animes = await axios.get('anime');
    if (!!animes.data) {
      this.animes = animes.data;
    }
    else {
      console.log("Ничего не найдено");
    }
  },
  computed: {
    computedColumnCount() {
      if (this.$vuetify.display.lgAndUp) { // если экран большой
        return 3;
      }
      else if (this.$vuetify.display.md || this.$vuetify.display.sm) { // если экран средний
        return 4;
      }
      else if (this.$vuetify.display.xs) {
        return 6;
      }
    }
  }
}
</script>

<style scoped>

</style>

