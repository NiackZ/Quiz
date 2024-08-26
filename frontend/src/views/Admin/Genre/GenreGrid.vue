<template>
  <v-card>
    <template v-slot:text>
      <v-text-field
          v-model="search"
          label="Поиск жанра"
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          density="compact"
          clearable
          hide-details
          single-line
      ></v-text-field>
    </template>
    <v-data-table-virtual density="comfortable"
                          show-select hover
                          :loading="loading"
                          v-model="selected"
                          :items="items"
                          :headers="headers"
                          :search="search"
    >
      <template v-slot:top>
        <v-toolbar density="compact" flat style="padding: 0 1rem; background-color: inherit;">
          <v-dialog v-model="dialog"
                    max-width="500px"
                    scrim="black">
            <template v-slot:activator="{ props }">
              <v-btn class=""
                     color="primary"
                     v-bind="props"
              >
                Добавить жанр
              </v-btn>
            </template>
            <v-card density="compact">
              <v-card-title class="text-center">
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>
              <v-card-text style="padding: 0">
                <v-container>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field v-model="editedItem.name"
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
                <v-btn color="red"
                       variant="text"
                       @click="close"
                >
                  Закрыть
                </v-btn>
                <v-btn color="blue-darken-1"
                       variant="text"
                       @click="save"
                >
                  Сохранить
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-btn class="" color="red" :disabled="selected.length === 0" @click="deleteSelected">Удалить</v-btn>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
            class="me-2"
            size="small"
            @click="editItem(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
            size="small"
            @click="deleteItem(item)"
        >
          mdi-delete
        </v-icon>
      </template>

      <template v-slot:loading>
        <v-skeleton-loader type="table-row"></v-skeleton-loader>
      </template>
    </v-data-table-virtual>
  </v-card>
</template>

<script>
import {getGenres} from "../../../axios/api/genres/genres.js";

export default {
  data() {
    return {
      headers: [
          { title: 'ИД', key: 'id' },
          { title: 'Название', key: 'name' },
          { title: 'Действия', key: 'actions', sortable: false, width: '100px' }
      ],
      dialog: false,
      loading: true,
      search: '',
      selected: [],
      items: [],
      editedIndex: -1,
      editedItem: {
        name: ''
      }
    }
  },
  async created() {
    const genresPromise = getGenres();
    this.items = (await genresPromise).data.map(genre => {
      return {
        id: genre.id,
        name: genre.name,
        actions: null
      }
    });
    const _this = this;
    setTimeout(function () {
      _this.loading = false;
    }, 500)
  },
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'Новый жанр' : 'Редактирование жанра';
    },
    computedColumnCount() {
      if (this.$vuetify.display.lgAndUp) { // если экран большой
        return 2;
      }
      else if (this.$vuetify.display.md || this.$vuetify.display.sm) { // если экран средний
        return 3;
      }
      else if (this.$vuetify.display.xs) {
        return 12;
      }
    }
  },
  methods: {
    editItem (item) {
      this.editedIndex = this.items.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },
    deleteItem (item) {
      this.editedIndex = this.items.indexOf(item)
      this.items.splice(this.editedIndex, 1)
    },
    deleteSelected() {
      console.log('delete selected');
    },
    close () {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem.name = '';
        this.editedIndex = -1;
      })
    },
    save() {

    }
  }
}
</script>

<style scoped>

</style>