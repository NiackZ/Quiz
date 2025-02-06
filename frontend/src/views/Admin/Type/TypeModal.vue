<template>
  <v-dialog v-model="dialogVisible" max-width="500px" scrim="black">
    <v-card density="compact">
      <v-card-title class="text-center">
        <span class="text-h5">{{ formTitle }}</span>
      </v-card-title>
      <v-card-text style="padding: 0">
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="editedItem.name"
                variant="outlined"
                hide-details
                autofocus
                label="Название"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red" variant="text" @click="close">Закрыть</v-btn>
        <v-btn color="blue-darken-1" variant="text" @click="saveOrCreate">
          Сохранить
        </v-btn>
      </v-card-actions>
    </v-card>
    <v-fade-transition>
      <v-alert
        class="alert-container"
        :text="alertText"
        type="error"
        variant="outlined"
        density="compact"
        v-model="showAlert"
      ></v-alert>
    </v-fade-transition>
  </v-dialog>
</template>

<script>
import { createType, saveType } from "../../../axios/api/types.js";

export default {
  props: {
    modelValue: Boolean,
    editedItem: Object,
  },
  emits: ["update:modelValue", "saved", "created"],
  data() {
    return {
      showAlert: false,
      alertText: null,
    };
  },
  computed: {
    dialogVisible: {
      get() {
        return this.modelValue;
      },
      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
    formTitle() {
      return this.editedItem.id === null ? "Новый тип" : "Редактирование типа";
    },
  },
  methods: {
    close() {
      this.dialogVisible = false;
    },
    async saveOrCreate() {
      try {
        if (this.editedItem.id === null) {
          await createType(this.editedItem);
          this.$emit("created");
        } else {
          await saveType(this.editedItem.id, this.editedItem);
          this.$emit("saved");
        }
        this.close();
      } catch (e) {
        this.alertText = e.response.data;
        this.showAlert = true;
        const _this = this;
        setTimeout(() => (_this.showAlert = false), 5000);
      }
    },
  },
};
</script>

<style scoped>
.alert-container {
  position: fixed;
  transform: translateY(calc(100% + 180px));
  width: 100%;
}
</style>