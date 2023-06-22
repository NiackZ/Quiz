<template>
  <v-container>
    <q-component-grid :elements="items" />
  </v-container>
</template>

<script>

import QComponentGrid from "../components/QContentGrid/QContentGrid.vue";

export default {
  components: {QComponentGrid},

  data() {
    return {
      items: [
        { id: 1 }, { id: 2 }, { id: 3 }, { id: 4 },
        { id: 5 }, { id: 6 }
      ]
    }
  }
}
</script>

<style>
</style>