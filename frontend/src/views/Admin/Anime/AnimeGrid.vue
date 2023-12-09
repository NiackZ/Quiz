<template>
  <v-row>
    <v-col v-for="(anime, index) in animes"
           :key="index"
           :cols="computedColumnCount"
           :style="{ height: this.cellHeight }"
    >
      <router-link :to="'/item/' + anime.id" class="text-decoration-none">
        <div class="d-flex justify-center grid-cell bg-img-center h-100 "
             :style="{ width: cellWidth, 'background-image': `url(${anime.posterURL})` }"
        >
          <div class="short-description">
            element #{{ anime.id }}
          </div>
        </div>
      </router-link>
    </v-col>
  </v-row>
</template>

<script>
import axios from "/src/axios/http-common.js";

export default {
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
      console.log("Ничего не найдено")
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

