<template>
  <v-container>
    <q-content-item :item-id="Number($route.params.id)" />
  </v-container>
</template>

<script>

import QContentItem from "../../components/QContentGrid/QContentItem.vue";

export default {
  components: {QContentItem}
}
</script>

<style scoped>

</style>