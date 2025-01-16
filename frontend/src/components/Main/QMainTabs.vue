<template>
  <v-tabs
      v-model="tab"
      align-tabs="center"
      color="deep-purple"
      density="compact"
      hide-slider
  >
    <v-tab v-for="tab in this.$props.items" :key="tab.text" :value="tab.text" :text="tab.text"/>
  </v-tabs>

  <v-tabs-window v-model="tab">
<!--    Подумать над анимацией-->
    <v-tabs-window-item transition="toggle-slide-y-transition"
        v-for="item in this.$props.items"
        :key="item.text"
        :value="item.text"
    >
      <q-content-grid :elements="item.list" :path="item.path"/>
    </v-tabs-window-item>
  </v-tabs-window>
</template>

<script>

import QContentGrid from "../QContentGrid/QContentGrid.vue";

export default {
  name: 'QMainTabs',
  components: {QContentGrid},
  props: {
    items: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      tab: null
    };
  },
  methods: {
  },
  created() {
    this.tab = this.$props.items.length > 0 ? this.$props.items[0] : null;
  },
};
</script>

<style scoped>

</style>