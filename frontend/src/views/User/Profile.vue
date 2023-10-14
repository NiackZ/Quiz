<template>
  <v-row>
    <v-col cols="4">
      <QFileUpload :with-preview="file.withPreview"/>
    </v-col>
    <v-col cols="8">
      <!-- Логин пользователя -->
      <v-text-field v-if="user" label="Логин"
                    v-model="user.username"
                    :disabled="true"
                    variant="underlined"
      />

      <!-- Поля для изменения пароля -->
      <v-text-field label="Текущий пароль"
                    type="password"
                    v-model="currentPassword"
                    variant="underlined"
      />

      <v-text-field label="Новый пароль"
                    type="password"
                    v-model="newPassword"
                    variant="underlined"
      />

      <!-- Кнопка для сохранения изменений -->
      <v-btn @click="saveChanges">Сохранить</v-btn>
    </v-col>
  </v-row>
</template>
<script>
import QFileUpload from "../../components/QFileUpload/QFileUpload.vue";
import {mapState} from "vuex";

export default {
  name: "Profile",
  components: {QFileUpload},
  data() {
    return {
      file: {
        withPreview: true,
      },
      currentPassword: "",
      newPassword: "",
    };
  },
  computed: {
    ...mapState({
      user: state => state.auth.user
    })
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