<template>
  <v-row>
    <v-col cols="12" md="5" lg="4">
      <QFileUpload ref="fileUpload"/>
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
                      v-model="form.episodeCount"
                      variant="underlined"
                      :error-messages="v$.form.episodeCount.$errors.map(e => e.$message)"
        />
        <v-text-field label="Продолжительность эпизода (в минутах)"
                      v-model="form.episodeDuration"
                      variant="underlined"
                      :error-messages="v$.form.episodeDuration.$errors.map(e => e.$message)"
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
import {useVuelidate} from '@vuelidate/core'
import {helpers, required} from '@vuelidate/validators'
import QLinkField from "../QLinkField/QLinkField.vue";
import QJoditEditor from "../QJoditEditor/QJoditEditor.vue";
import QVueDatePicker from "../QVueDatePicker/QVueDatePicker.vue";
import QFileUpload from "../QFileUpload/QFileUpload.vue";
import {encodeImage, getGenres, getMarks, getStatuses, getStudios, getTypes} from "../../utils/utils.js";
import axios from '/src/axios/http-common'

export default {
  name: 'QItemCreate',
  components: {QFileUpload, QVueDatePicker, QJoditEditor, QLinkField},
  data() {
    return {
      form: {
        rusName: "3",
        romName: "4",
        type: {
          value: null,
          list: []
        },
        genre: {
          value: [],
          list: []
        },
        studio: {
          value: [],
          list: []
        },
        status: {
          value: null,
          list: []
        },
        period: null,
        episodeCount: 0,
        episodeDuration: 0,
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
      v$: useVuelidate({$scope: 'form'})
    }
  },
  props: {
    itemId: Number
  },
  async created() {
    const typesPromise = getTypes();
    const genresPromise = getGenres();
    const studiosPromise = getStudios();
    const statusesPromise = getStatuses();
    const marksPromise = getMarks();

    this.form.type.list = (await typesPromise).data;
    this.form.genre.list = (await genresPromise).data;
    this.form.studio.list = (await studiosPromise).data;
    this.form.status.list = (await statusesPromise).data;
    this.form.marks.list = (await marksPromise).data;
  },
  mounted() {
    document.title = `Создание нового элемента` // устанавливаем заголовок страницы
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
    async isValid(){
      return await this.v$.$validate();
    },
    async createBtn(){
      if (!await this.isValid()) return;
      const file = this.$refs.fileUpload.getFile();
      const posterPromise = encodeImage(file);
      const period = this.form.period;
      const requestData = {
        poster: await posterPromise,
        rusName: this.form.rusName,
        romName: this.form.romName,
        typeId: this.form.type.value,
        genreIds: this.form.genre.value,
        studioIds: this.form.studio.value,
        statusId: this.form.status.value,
        period: period[0] !== undefined ? period : [period], // суть в том, чтобы в любом случае передать массив
        episodeCount: this.form.episodeCount,
        episodeDuration: this.form.episodeDuration,
        linkList: this.form.links,
        markIds: this.form.marks.value,
        description: this.form.description
      };

      console.log(requestData);
      await axios.post("/anime", requestData);
      // try {
      //   const response = await this.$axios.post('/api/createAnime', requestData);
      //
      //   // Обработка успешного ответа
      //   console.log('Anime успешно создан:', response.data);
      // } catch (error) {
      //   // Обработка ошибки
      //   console.error('Ошибка при создании Anime:', error);
      // }

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
        episodeCount: {
          required: helpers.withMessage(`Заполните количество эпизодов.`, required)
        },
        episodeDuration: {
          required: helpers.withMessage(`Заполните продолжительность эпизода.`, required)
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