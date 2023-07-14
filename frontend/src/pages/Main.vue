<template>
  <v-container>
    <div>
      <p>Count: {{ count }}</p>
      <button @click="incrementCount">Increment</button>
    </div>
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
  },
  computed: {
    count() {
      return this.$store.state.count.count;
    }
  },
  methods: {
    incrementCount() {
      this.$store.commit('count/increment');
    }
  }
}
</script>

<style>
</style>