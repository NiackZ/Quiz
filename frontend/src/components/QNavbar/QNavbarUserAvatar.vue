<template>
  <v-menu rounded
          location="bottom end"
          origin="auto"
  >
    <template v-slot:activator="{ props }">
      <v-btn icon v-bind="props">
        <v-avatar>
          <v-icon icon="mdi-account-circle"/>
        </v-avatar>
      </v-btn>
    </template>
    <v-list style="border: 1px solid red; padding: 0;margin-top: 10px">
      <template v-for="(item, index) in items" :key="index">
        <v-list-item density="compact" v-if="!item.type" @click="item.action && item.action()">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
        <v-divider v-if="item.type === 'divider'" :key="'divider-' + index"/>
      </template>
    </v-list>
  </v-menu>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: "QNavbarUserAvatar",
  data() {
    return {
      showMenu: false,
      items: [
        { title: 'Профиль' },
        { type: 'divider' },
        { title: 'Выход', action: this.logout }
      ]
    };
  },
  methods: {
    ...mapActions('auth', ['logout'])
  }
}
</script>

<style scoped>

</style>