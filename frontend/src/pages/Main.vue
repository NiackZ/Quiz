<template>
  <v-container>
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
  </v-container>
</template>

<script>

export default {

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