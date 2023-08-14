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
    <v-list :items="items"/>
  </v-menu>
</template>

<script>
export default {
  name: "QNavbarUserAvatar",
  data() {
    return {
      showMenu: false,
      items: [
        { title: 'Профиль' },
        { type: 'divider' },
        { title: 'Выход' },
      ]
    };
  }
}
</script>

<style scoped>

</style>