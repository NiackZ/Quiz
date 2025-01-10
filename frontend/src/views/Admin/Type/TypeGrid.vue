<template>
  <v-card>
    <template v-slot:text>
      <v-text-field
          v-model="search"
          label="Поиск типа"
          prepend-inner-icon="mdi-magnify"
          variant="outlined"
          density="compact"
          clearable
          hide-details
          single-line
      ></v-text-field>
    </template>
    <v-data-table-virtual density="comfortable"
                          hover
                          :loading="isLoading"
                          :items="showDeleted ? items : filteredItems"
                          :row-props="rowFunc"
                          :headers="headers"
                          :search="search"
                          :sort-by="[{ key: 'id', order: 'desc' }]"
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
                Добавить тип
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
        <v-icon v-if="!item.isDeleted"
                size="small"
                @click="deleteItem(item)"
        >
          mdi-delete
        </v-icon>
        <v-icon v-else
                size="small"
                @click="restoreItem(item)"
        >
          mdi-restore
        </v-icon>
      </template>

      <template v-slot:loading>
        <v-skeleton-loader type="table-row"></v-skeleton-loader>
      </template>
    </v-data-table-virtual>
  </v-card>
</template>

<script>
import {getTypes, saveType, createType, deleteType} from "../../../axios/api/types.js";

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
      isLoading: true,
      search: '',
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
      return this.editedItem.id === null ? 'Новый тип' : 'Редактирование типа';
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
        this.isLoading = true;
        this.items = (await getTypes()).data.map(type => {
          return {
            id: type.id,
            name: type.name,
            isDeleted: type.isDeleted
          }
        });
        this.filteredItems = this.items.filter(item => !item.isDeleted);
        const _this = this;
        setTimeout(function () {
          _this.isLoading = false;
          console.log('list reloaded');
        }, 200)
      }
      catch (e) {
        this.isLoading = false;
      }
    },
    editItem (item) {
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },
    async deleteItem (item) {
      await deleteType(item.id);
      await this.reloadList();
    },
    async restoreItem (item) {
      try {
        item.isDeleted = false;
        await saveType(item.id, item);
        await this.reloadList();
      }
      catch (e) {
        console.error(e.response.data);
      }
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
        await createType(this.editedItem);
        await this.reloadList();
        this.close();
      }
      catch (e) {
        console.error(e.response.data);
      }
    },
    async save() {
      try {
        await saveType(this.editedItem.id, this.editedItem);
        await this.reloadList();
        this.close();
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