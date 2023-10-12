<template>
  <v-container>
    <v-row>
      <v-col cols="4">
        <QFileUpload :with-preview="file.withPreview"/>
      </v-col>
      <v-col cols="8">
        <!-- Логин пользователя -->
        <v-text-field
            label="Логин"
            v-model="login"
        ></v-text-field>

        <!-- Поля для изменения пароля -->
        <v-text-field
            label="Текущий пароль"
            type="password"
            v-model="currentPassword"
        ></v-text-field>

        <v-text-field
            label="Новый пароль"
            type="password"
            v-model="newPassword"
        ></v-text-field>

        <!-- Кнопка для сохранения изменений -->
        <v-btn @click="saveChanges">Сохранить</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import QFileUpload from "../../components/QFileUpload/QFileUpload.vue";

export default {
  name: "Profile",
  components: {QFileUpload},
  data() {
    return {
      file: {
        withPreview: true,
      },
      login: "",
      currentPassword: "",
      newPassword: "",
    };
  },
  created() {
    console.log("created...");
  },
  methods: {
    saveChanges() {
      // Реализация сохранения изменений
    },
  }
}
</script>
<style scoped>

</style>