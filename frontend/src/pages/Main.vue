<template>
  <v-container>
    <!--
    Some text
    <v-menu
        open-on-hover
        :open-delay="50"
        :close-delay="50"
    >
      <template v-slot:activator="{ props }">
        <v-btn
            color="primary"
            v-bind="props"
        >
          Dropdown
        </v-btn>
      </template>

      <v-list>
        <v-list-item
            v-for="(item, index) in items"
            :key="index"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    -->
    <q-component-grid
        :row-count="3"
        :column-count="4"
    />
  </v-container>
</template>

<script>

import QComponentGrid from "../components/QContentGrid/QContentGrid.vue";

export default {
  components: {QComponentGrid},

  data() {
    return {
      items: [
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me' },
        { title: 'Click Me 2' },
      ],
      routes: [
        {
          path: '/admin',
          title: 'Adminka'
        }
      ]
    }
  }
}
</script>

<style>
</style>