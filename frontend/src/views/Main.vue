<template>
  <q-component-grid :elements="items" />
</template>

<script>
import QComponentGrid from "../components/QContentGrid/QContentGrid.vue";
import { mapState, mapMutations } from 'vuex';
export default {
  components: {QComponentGrid},

  data() {
    return {
      items: [
        { id: 1 }, { id: 2 }, { id: 3 }, { id: 4 },
        { id: 5 }, { id: 6 }
      ]
    }
  },
  computed: {
    ...mapState({
      count: state => state.count.count
    })
  },
  methods: {
    ...mapMutations('count', ['increment'])
  }
}
</script>

<style scoped>

</style>