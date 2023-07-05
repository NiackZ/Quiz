<template>
  <v-row>
    <v-col cols="12" md="5" lg="4">
      <v-radio-group v-model="uploadValue">
        <v-radio :value="UPLOAD_METHOD.FILE" label="Загрузить с ПК"/>
        <v-radio :value="UPLOAD_METHOD.URL" label="Загрузить по ссылке"/>
      </v-radio-group>
      <v-file-input
          v-if="uploadValue === UPLOAD_METHOD.FILE"
          variant="underlined"
          label="Выберите файл"
          v-model="file"
          :show-size="true"
          :show-progress="true"
          accept=".jpg, .jpeg, .png"
          @click:clear="clearFile"
      />
      <v-text-field
          v-if="uploadValue === UPLOAD_METHOD.URL"
          v-model="imageUrl"
          label="Введите URL-адрес изображения"
          @input="previewImage"
          prepend-icon="mdi-link-variant"
          clearable
          variant="underlined"
      />
      <v-img :src="imageSrc" contain/>
    </v-col>
    <v-col cols="12" md="7" lg="8" class="ff-verdana">
      <v-form>
        <v-text-field label="Название на русском"
                      v-model="form.rusName"
                      variant="underlined"
                      :error-messages="v$.form.rusName.$errors.map(e => e.$message)"
        />
        <v-text-field label="Название на ромадзи"
                      v-model="form.romName"
                      variant="underlined"
                      :error-messages="v$.form.romName.$errors.map(e => e.$message)"
        />
        <v-autocomplete label="Тип"
                        v-model="form.type.value"
                        :items="form.type.list"
                        item-value="id" item-title="name"
                        variant="underlined"
                        :error-messages="v$.form.type.value.$errors.map(e => e.$message)"
        />
        <v-autocomplete label="Жанр"
                        v-model="form.genre.value"
                        :items="form.genre.list"
                        item-value="id" item-title="name"
                        variant="underlined"
                        multiple
                        :error-messages="v$.form.genre.value.$errors.map(e => e.$message)"
        />
        <v-autocomplete label="Студия"
                        v-model="form.studio.value"
                        :items="form.studio.list"
                        item-value="id" item-title="name"
                        variant="underlined"
                        multiple
                        :error-messages="v$.form.studio.value.$errors.map(e => e.$message)"
        />
        <v-autocomplete label="Статус"
                        v-model="form.status.value"
                        :items="form.status.list"
                        item-value="id" item-title="name"
                        variant="underlined"
                        :error-messages="v$.form.status.value.$errors.map(e => e.$message)"
        />
        <v-text-field label="Количество эпизодов"
                      v-model="form.duration.seriesCount"
                      variant="underlined"
                      :error-messages="v$.form.duration.seriesCount.$errors.map(e => e.$message)"
        />
        <v-text-field label="Продолжительность эпизода"
                      v-model="form.duration.seriesDuration"
                      variant="underlined"
                      :error-messages="v$.form.duration.seriesDuration.$errors.map(e => e.$message)"
        />
        <q-vue-date-picker :date="form.period"
                           @update:date="updateDate"
                           placeholder="Выберите период выпуска"
                           :error-messages="v$.form.period.$errors.map(e => e.$message)"
        />
        <q-link-field :links="form.links" @update:links="updateLinks"/>
        <v-autocomplete label="Метки"
                        v-model="form.marks.value"
                        :items="form.marks.list"
                        item-value="id" item-title="name"
                        variant="underlined"
                        clearable
                        multiple
        />
        <q-jodit-editor :text="form.description" @update:text="updateDescription" />
        <div class="py-4 text-end">
          <v-btn color="primary"
                 min-width="92"
                 variant="outlined"
                 class="ml-3"
                 @click="createBtn"
          >
            Добавить
          </v-btn>
        </div>
      </v-form>
    </v-col>
  </v-row>
</template>

<script>
import { useVuelidate } from '@vuelidate/core'
import { required, helpers } from '@vuelidate/validators'
import { UPLOAD_METHOD } from '/src/constants/constants';
import QLinkField from "../QLinkField/QLinkField.vue";
import QJoditEditor from "../QJoditEditor/QJoditEditor.vue";
import QVueDatePicker from "../QVueDatePicker/QVueDatePicker.vue";

export default {
  name: 'QItemCreate',
  components: {QVueDatePicker, QJoditEditor, QLinkField},
  data() {
    return {
      form: {
        rusName: "3",
        romName: "4",
        type: {
          value: null,
          list: [ {id: 1, name: 'ТВ'}, {id: 2, name: 'Фильм'} ]
        },
        genre: {
          value: [],
          list: [ {id: 1, name: 'Фэнтази'}, {id: 2, name: 'Школа'} ]
        },
        studio: {
          value: [],
          list: [ {id: 1, name: 'Ufotable'}, {id: 2, name: 'Kyoto Animation'} ]
        },
        status: {
          value: [],
          list: [ {id: 1, name: 'Вышел'}, {id: 2, name: 'Онгоинг'} ]
        },
        period: null,
        duration: {
          seriesCount: 0,
          seriesDuration: 0
        },
        links: [
          {
            name: "URL1",
            url: "https://yandex.ru"
          },
          {
            name: "URL2",
            url: "https://google.ru"
          },
          {
            name: "URL3",
            url: "https://yandex.ru"
          },
          {
            name: "URL4",
            url: "https://google.ru"
          },
          {
            name: "URL5",
            url: "https://yandex.ru"
          },
          {
            name: "URL6",
            url: "https://google.ru"
          }
        ],
        marks: {
          value: [],
          list: [{id: 1, name: 'BDRip'}]
        },
        description: '123'
      },
      imageUrl: '',
      previewUrl: '',
      file: null,
      uploadValue: UPLOAD_METHOD.FILE,
      UPLOAD_METHOD: UPLOAD_METHOD,
      v$: useVuelidate({$scope: 'form'})
    }
  },
  props: {
    itemId: Number
  },
  mounted() {
    document.title = `Создание нового элемента` // устанавливаем заголовок страницы
  },
  computed: {
    imageSrc() {
      if (this.uploadValue === UPLOAD_METHOD.FILE && this.file) {
        return URL.createObjectURL(this.file[0])
      } else if (this.uploadValue === UPLOAD_METHOD.URL && this.previewUrl) {
        return this.previewUrl
      }
      return ''
    }
  },
  methods: {
    updateDate(newValue) {
      this.form.period = newValue;
    },
    updateDescription(newValue) {
      this.form.description = newValue;
    },
    updateLinks(newValue) {
      this.form.links = newValue;
    },
    clearFile() {
      this.file = null
    },
    previewImage() {
      this.previewUrl = this.imageUrl
    },
    async isValid(){
      return await this.v$.$validate();
    },
    async createBtn(){
      console.log('click')
      if (!await this.isValid()) return;
      console.log('field`s are valid')
    }
  },
  validations () {
    return {
      form: {
        rusName: {
          required: helpers.withMessage(`Заполните русское название.`, required)
        },
        romName: {
          required: helpers.withMessage(`Заполните название на ромадзи.`, required)
        },
        type: {
          value: {
            required: helpers.withMessage(`Выберите тип.`, required)
          }
        },
        genre: {
          value: {
            required: helpers.withMessage(`Выберите жанр.`, required)
          }
        },
        studio: {
          value: {
            required: helpers.withMessage(`Выберите студию.`, required)
          }
        },
        status: {
          value: {
            required: helpers.withMessage(`Выберите статус.`, required)
          }
        },
        duration: {
          seriesCount: {
            required: helpers.withMessage(`Заполните количество эпизодов.`, required)
          },
          seriesDuration: {
            required: helpers.withMessage(`Заполните продолжительность эпизода.`, required)
          }
        },
        period: {
          required: helpers.withMessage(`Выберите период.`, required)
        }
      }
    }
  }
}
</script>

<style scoped>
.v-img {
  border-radius: 3px;
}
.ff-verdana {
  font-family: Verdana, serif;
}
.mr2-0{
  margin: 2px 0px;
}
.mrl5{
  margin-left: 5px;
}
.mr10-0{
  margin: 10px 0px;
}
.span-tag {
  margin-top: 5px;
  margin-left: 5px;
  cursor: pointer;
  display: inline-block;
  padding: 0px 5px;
  border-radius: 3px;
  background-color: rgba(25, 88, 255, 0.5);
  transition: 0.1s;
}
.span-tag:hover {
  color: gold;
}
.item-link{
  color: gold;
  text-decoration: none;
}
.item-link:hover{
  text-decoration: underline;
}
.item_description{

}
</style>