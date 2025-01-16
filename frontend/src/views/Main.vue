<template>
  <q-main-tabs/>
  <q-component-grid :elements="items" />
</template>

<script>
import QComponentGrid from "../components/QContentGrid/QContentGrid.vue";
import {getAnimeInfoForGrid} from "../utils/utils.js";
import QMainTabs from "../components/Main/QMainTabs.vue";

export default {
  components: {QMainTabs, QComponentGrid },
  data() {
    return {
      items: []
    };
  },
  methods: {
    async fetchItems() {
      this.items = (await getAnimeInfoForGrid()).data;
    },
  },
  created() {
    this.fetchItems();
  },
};
</script>

<style scoped>

</style>