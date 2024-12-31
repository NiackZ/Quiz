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
                          :items="showDeleted ? items : filteredItems"
                          :row-props="rowFunc"
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
                       @click="this.editedItem.id === null ? create() : save()"
                >
                  Сохранить
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-checkbox
              class="q-checkbox"
              v-model="showDeleted"
              label="Показать удалённые"
              color="primary"
              :hide-details="true"
              density="comfortable"
          />
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
            :disabled="item.isDeleted"
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
import {getGenres, saveGenre, createGenre, deleteGenre} from "../../../axios/api/genres/genres.js";

export default {
  data() {
    return {
      headers: [
          { title: 'ИД', key: 'id' },
          { title: 'Название', key: 'name' },
          { title: 'Действия', key: 'actions', sortable: false, width: '100px' }
      ],
      showDeleted: false,
      dialog: false,
      loading: true,
      search: '',
      selected: [],
      items: [],
      filteredItems: [],
      editedItem: {
        id: null,
        name: null
      }
    }
  },
  async created() {
    await this.reloadList();
  },
  computed: {
    formTitle () {
      return this.editedItem.id === null ? 'Новый жанр' : 'Редактирование жанра';
    }
  },
  methods: {
    rowFunc(row) {
      if (row.item.isDeleted) {
        return {
          class: 'deleted-row'
        }
      }
    },
    async reloadList() {
      try{
        this.loading = true;
        this.items = (await getGenres()).data.map(genre => {
          return {
            id: genre.id,
            name: genre.name,
            isDeleted: genre.isDeleted
          }
        });
        this.filteredItems = this.items.filter(item => !item.isDeleted);
        const _this = this;
        setTimeout(function () {
          _this.loading = false;
          console.log('list reloaded');
        }, 200)
      }
      catch (e) {
        this.loading = false;
      }
    },
    editItem (item) {
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    async deleteItem (item) {
      await deleteGenre(item.id);
      console.log("deleted");
      await this.reloadList();
    },
    deleteSelected() {
      console.log('delete selected');
    },
    close () {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem.name = null;
        this.editedItem.id = null;
      })
    },
    async create() {
      try {
        await createGenre(this.editedItem);
        await this.reloadList();
      }
      catch (e) {
        console.error(e.response.data);
      }
    },
    async save() {
      try {
        await saveGenre(this.editedItem.id, this.editedItem);
        await this.reloadList();
      }
      catch (e) {
        console.error(e.response.data);
      }
    }
  }
}
</script>

<style scoped>

</style>