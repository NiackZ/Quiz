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
        <v-text-field variant="underlined" label="Название" v-model="linkName"/>
        <v-text-field variant="underlined" label="URL" v-model="linkUrl"/>
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
    };
  },
  methods: {
    appendIconClick(item) {
      this.editedLink = item;
      this.linkName = item.value.name;
      this.linkUrl = item.value.url;
      this.showModal = true;
    },
    addLink() {
      this.links.push({
        name: this.linkName,
        url: this.linkUrl
      });
      this.cancelLink();
    },
    editLink() {
      this.editedLink.value.name = this.linkName;
      this.editedLink.value.url = this.linkUrl;
      this.cancelLink();
    },
    cancelLink() {
      this.showModal = false;
      this.linkName = '';
      this.linkUrl = '';
      this.editedLink = null;
    }
  },
}
</script>
<style>

</style>