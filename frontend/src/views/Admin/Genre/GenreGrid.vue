<template>
  111
</template>

<script>
import axios from "/src/axios/http-common.js";


export default {
  methods: {

  },
  data() {
    return {
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

</style>