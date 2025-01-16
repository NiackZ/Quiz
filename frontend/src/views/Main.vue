<template>
  <q-main-tabs v-if="items.length > 0" :items="items"/>
</template>

<script>
import QComponentGrid from "../components/QContentGrid/QContentGrid.vue";
import {getAnimeInfoForGrid} from "../utils/utils.js";
import QMainTabs from "../components/Main/QMainTabs.vue";
import {PATH} from "../constants/constants.js";

export default {
  computed: {
  },
  components: {QMainTabs, QComponentGrid},
  data() {
    return {
      items: []
    };
  },
  methods: {
    async fetchItems() {
      try {
        const animePromise = getAnimeInfoForGrid();

        const anime = {
          text: "Аниме",
          path: PATH.ANIME,
          list: null
        }
        const manga = {
          text: "Манга",
          path: PATH.MANGA,
          list: null
        }
        const ranobe = {
          text: "Ранобэ",
          path: PATH.RANOBE,
          list: null
        }

        anime.list = (await animePromise).data;
        manga.list = (await animePromise).data;//todo
        ranobe.list = (await animePromise).data;//todo

        this.items = [anime, manga, ranobe];
      } catch (error) {
        console.error("Error in fetchItems:", error);
      }
    },
  },
  async created() {
    await this.fetchItems();
  }
};
</script>

<style scoped>

</style>