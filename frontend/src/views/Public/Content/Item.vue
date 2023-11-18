<template>
  <q-content-item :item-id="Number(id)" />
</template>

<script>

import QContentItem from "../../../components/QItem/QContentItem.vue";

export default {
  components: {QContentItem},
  props: ['id']
}
</script>

<style scoped>

</style>