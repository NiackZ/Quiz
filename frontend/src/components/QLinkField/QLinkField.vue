<template>
  <v-combobox :model-value="links"
              readonly
              chips
              label="Ссылки"
              multiple
              variant="underlined"
              :hide-details="true"
              @update:model-value="$emit('update:links', $event)"
  >
    <template v-slot:chip="{ item, index, props }">
      <v-chip v-bind="props"
              class="mr-2"
              color="primary"
              label
              closable
              append-icon="mdi-pencil"
              @click:append="appendIconClick"
      >
        {{item.value.name}}
        <v-tooltip activator="parent" location="bottom">
          {{item.value.url}}
        </v-tooltip>
        <template v-slot:append>
          <v-icon icon="mdi-pencil" v-on:click="appendIconClick(item)"/>
        </template>
      </v-chip>
    </template>
  </v-combobox>
  <v-btn class="my-2"
         color="primary"
         dark
         @click="showModal = true"
         variant="tonal"
  >
    Добавить ссылку
  </v-btn>
  <v-dialog v-model="showModal"
            max-width="500px"
            :scrim="'black'"
  >
    <v-card>
      <v-toolbar class="text-center"
                 color="primary"
                 :title="`${this.editedLink ? 'Редактирование': 'Добавление'} ссылки`"
      />
      <v-card-text>
        <v-text-field variant="underlined"
                      label="Название"
                      v-model="linkName"
                      :error-messages="v$.linkName.$errors.map(e => e.$message)"
        />
        <v-text-field variant="underlined"
                      label="Ссылка"
                      v-model="linkUrl"
                      :error-messages="v$.linkUrl.$errors.map(e => e.$message)"
        />
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn color="pink-lighten-1" @click="cancelLink">Отмена</v-btn>
        <v-btn v-if="this.editedLink" color="primary" @click="editLink">Сохранить</v-btn>
        <v-btn v-else color="primary" @click="addLink">Добавить</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script>
import { useVuelidate } from '@vuelidate/core'
import { required, url, minLength, helpers } from '@vuelidate/validators'

export default {
  name:"QLinkField",
  props: {
    links: Object
  },
  data() {
    return {
      editedLink: null,
      showModal: false,
      linkName: '',
      linkUrl: '',
      v$: useVuelidate()
    };
  },
  methods: {
    appendIconClick(item) {
      this.editedLink = item;
      this.linkName = item.value.name;
      this.linkUrl = item.value.url;
      this.showModal = true;
    },
    async isValid(){
      return await this.v$.$validate();
    },
    async addLink() {
      if (!await this.isValid()) return;
      this.links.push({
        name: this.linkName,
        url: this.linkUrl
      });
      this.cancelLink();
    },
    async editLink() {
      if (!await this.isValid()) return;
      this.editedLink.value.name = this.linkName;
      this.editedLink.value.url = this.linkUrl;
      this.cancelLink();
    },
    cancelLink() {
      this.showModal = false;
      this.linkName = '';
      this.linkUrl = '';
      this.editedLink = null;
      this.v$.$reset()
    }
  },
  validations () {
    return {
      linkName: {
        required: helpers.withMessage(`Поле должно быть заполнено.`, required),
        minLength: helpers.withMessage(
            ({$params,}) => `Поле должно быть больше ${$params.min} символов.`,
            minLength(3)
        )
      },
      linkUrl: {
        required: helpers.withMessage(`Поле должно быть заполнено.`, required),
        url: helpers.withMessage(`Некорректный URL-адрес.`, url)
      }
    }
  }
}
</script>
<style>

</style>