<template>
  <q-main-tabs v-if="items.length > 0" :items="items"/>
</template>

<script>
import QComponentGrid from "../components/QContentGrid/QContentGrid.vue";
import {getAnimeInfoForGrid} from "../utils/utils.js";
import QMainTabs from "../components/Main/QMainTabs.vue";
import {PATH} from "../constants/constants.js";
import {mapGetters} from "vuex";

export default {
  components: {QMainTabs, QComponentGrid},
  data() {
    return {
      items: [
        { text: "Аниме", path: PATH.ANIME, list: [] }
      ],
    };
  },
  computed: {
    ...mapGetters("search", ["getAnimeResults"]),
  },
  watch: {
    getAnimeResults(newResults) {
      this.items[0].list = newResults;
    },
  },
  methods: {
    async fetchItems() {
      try {
        const animeData = await getAnimeInfoForGrid();
        this.items[0].list = animeData.data;
      } catch (error) {
        console.error("Ошибка в fetchItems:", error);
      }
    },
  },
  async created() {
    await this.fetchItems();
  },
};
</script>
