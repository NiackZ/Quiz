<template>
  <v-row>
    <v-col cols="3">
      <q-file-upload ref="fileUpload"
                     v-if="user"
                     :with-preview="true"
                     :poster-url="user.url" />
    </v-col>
    <v-col cols="9">
      <v-text-field v-if="user" label="Логин"
                    v-model="user.username"
                    :disabled="true"
                    variant="underlined"
      />

      <v-text-field v-if="user" label="Email"
                    v-model="email"
                    variant="underlined"
      />

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
import {encodeImage, isEmpty, updateUser} from "../../utils/utils.js";

export default {
  name: "Profile",
  components: {QFileUpload},
  data() {
    return {
      currentPassword: null,
      newPassword: null,
      email: null
    };
  },
  mounted() {
    if (this.user) {
      this.email = this.user.email;
    }
  },
  computed: {
    ...mapState({
      user: state => state.auth.user
    })
  },
  methods: {
    async saveChanges() {
      console.log("save");
      const file = this.$refs.fileUpload.getFile();

      await updateUser({
        id: this.user.id,
        password: this.currentPassword,
        newPassword: this.newPassword,
        email: this.email,
        avatar: await encodeImage(file)
      });
    },
  }
}
</script>
<style scoped>
.v-input__control {
  overflow: hidden !important;
}
</style>