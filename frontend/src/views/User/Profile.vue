<template>
  <v-row>
    <v-col cols="3">
      <q-file-upload ref="fileUpload"
                     v-if="user"
                     :with-preview="true"
                     :poster-url="user.url" />
    </v-col>
    <v-col cols="9">
      <v-form validate-on="submit lazy" @submit.prevent="submit">
        <v-text-field
            v-model="userName"
            :rules="rules"
            label="User name"
        ></v-text-field>

        <v-btn
            :loading="loading"
            class="mt-2"
            text="Submit"
            type="submit"
            block
        ></v-btn>
      </v-form>
      <v-text-field v-if="user" label="Логин"
                    v-model="user.username"
                    :disabled="true"
                    variant="underlined"
      />

      <v-text-field v-if="user" label="Email"
                    v-model="email"
                    variant="underlined"
                    :rules="[rules.required]"
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
  <v-alert
      v-model="alert"
      type="error"
      title="Ошибка"
      icon="false"
      density="compact"
  >
    <template v-slot:prepend></template>
    <template v-slot:text>{{alert.text}}</template>
  </v-alert>
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
      alert: {
        text: '123123'
      },
      rules: {
        required: value => !!value || 'Заполните поле',
      },
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
    async submit() {

    },
    async saveChanges() {
      let passRequeired = false;
      if (isEmpty(this.email)) {
        this.alert.text = '';
      }
      if (this.user.email !== this.email) {
        passRequeired = true;
      }
      if (passRequeired){

        return;
      }
      await updateUser({
        id: this.user.id,
        password: this.currentPassword,
        newPassword: this.newPassword,
        email: this.email,
        avatar: await encodeImage(this.$refs.fileUpload.getFile())
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