<template>
  <q-anime-detail :id="Number(id)" />
</template>

<script>
import QAnimeDetail from "../../../components/Anime/QAnimeDetail/QAnimeDetail.vue";

export default {
  components: {QAnimeDetail},
  props: ['id']
}
</script>

<style scoped>

</style>