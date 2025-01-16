<template>
  <v-tabs
      v-model="tab"
      align-tabs="center"
      color="deep-purple"
      density="compact"
  >
    <v-tab v-for="tab in items" :key="tab.text" :value="tab.text" :text="tab.text"/>
  </v-tabs>

  <v-tabs-window v-model="tab">
    <v-tabs-window-item
        v-for="item in items"
        :key="item.text"
        :value="item.text"
    >
      <v-card color="transparent">
        <v-card-text>{{ text }}</v-card-text>
      </v-card>
    </v-tabs-window-item>
  </v-tabs-window>
</template>

<script>

export default {
  name: 'QMainTabs',
  data() {
    return {
      tab: null,
      items: [
        {text: "Аниме"},
        {text: "Манга"},
        {text: "Ранобэ"}
      ],
      text: "Somebody wanna told me" +
          "The world is gonna hold me",
    };
  },
  methods: {
  },
  created() {
    this.tab = this.items[0];
  },
};
</script>

<style scoped>

</style>