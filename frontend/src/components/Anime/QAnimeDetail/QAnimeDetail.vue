<template>
  <v-row>
    <v-col cols="12" md="5" lg="4">
      <q-file-upload v-if="anime?.posterURL" ref="fileUpload" :poster-url="anime.posterURL" />
      <q-file-upload v-else ref="fileUpload" />
    </v-col>
    <v-col cols="12" md="7" lg="8" class="ff-verdana">
      <v-form>
        <v-text-field v-if="this.anime?.id" label="ИД"
                      v-model="this.anime.id"
                      variant="underlined"
                      disabled
        />
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
                 @click="anime ? saveAnime() : createAnime()"
          >
            {{ anime ? 'Сохранить' : 'Создать' }}
          </v-btn>
        </div>
      </v-form>
    </v-col>
  </v-row>
</template>

<script>
import {useVuelidate} from '@vuelidate/core'
import {helpers, required} from '@vuelidate/validators'
import QLinkField from "../../QLinkField/QLinkField.vue";
import QJoditEditor from "../../QJoditEditor/QJoditEditor.vue";
import QVueDatePicker from "../../QVueDatePicker/QVueDatePicker.vue";
import QFileUpload from "../../QFileUpload/QFileUpload.vue";
import {encodeImage, getGenres, getMarks, getStatuses, getStudios, getTypes} from "../../../utils/utils.js";
import axios from '/src/axios/http-common'

export default {
  name: 'QAnimeDetail',
  components: {QFileUpload, QVueDatePicker, QJoditEditor, QLinkField},
  data() {
    return {
      form: {
        rusName: "",
        romName: "",
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
        episodeCount: 0,
        episodeDuration: 0,
        period: null,
        links: [],
        marks: {
          value: [],
          list: []
        },
        description: ''
      },
      anime: null,
      v$: useVuelidate({$scope: 'form'})
    }
  },
  props: {
    id: Number
  },
  async created() {
    const animeId = this.$props.id;
    let animePromise = null;
    if (animeId) {
      animePromise = axios.get(`/anime/${this.$props.id}`);
    }
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

    if (animePromise !== null) {
      this.anime = (await animePromise).data;
      console.log("Аниме получено из БД");
      this.form.rusName = this.anime.ruName;
      this.form.romName = this.anime.romajiName;
      this.form.type.value = this.anime.type.id;
      this.form.genre.value.push(...this.anime.genres.map(genre => genre.id));
      this.form.studio.value.push(...this.anime.studios.map(studio => studio.id));
      this.form.status.value = this.anime.status.id;
      this.form.rusName = this.anime.ruName;
      this.form.romName = this.anime.romajiName;
      this.form.episodeCount = this.anime.episodeCount;
      this.form.episodeDuration = this.anime.episodeDuration;
      const startDate = this.anime.startDate ? new Date(this.anime.startDate) : null;
      const endDate = this.anime.endDate ? new Date(this.anime.endDate) : null;
      if (startDate) {
        this.form.period = endDate
            ? [startDate, endDate]
            : startDate
      }
      this.form.links = this.anime.links;
      this.form.marks.value = this.anime.marks;
      this.form.description = this.anime.description;
    }
  },
  mounted() {
    document.title = this.$props.id ? 'Редактирование аниме' : 'Добавление нового аниме';
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
    async getFormData() {
      const file = this.$refs.fileUpload.getFile();
      const posterPromise = encodeImage(file);
      const period = this.form.period;
      return {
        poster: await posterPromise,
        rusName: this.form.rusName,
        romName: this.form.romName,
        typeId: this.form.type.value,
        genreIds: this.form.genre.value,
        studioIds: this.form.studio.value,
        statusId: this.form.status.value,
        episodeCount: this.form.episodeCount,
        episodeDuration: this.form.episodeDuration,
        period: period[0] !== undefined ? period : [period], // суть в том, чтобы в любом случае передать массив
        linkList: this.form.links,
        markIds: this.form.marks.value,
        description: this.form.description
      };
    },
    async createAnime() {
      if (!await this.isValid()) return;
      const createData = this.getFormData();
      try {
        const response = await axios.post("/anime", await createData);
        console.log('Anime успешно создан: ', response.data);
      }
      catch (error) {
        console.error('Ошибка при создании Anime: ', error);
      }
    },
    async saveAnime() {
      //todo
      const requestData = await this.getFormData();
      var jsonString = JSON.stringify(requestData);
      requestData.anime = this.anime;
      var jsonString2 = JSON.stringify(requestData);

// Измеряем размер строки в байтах
      var byteSize = new TextEncoder().encode(jsonString).length;
      var byteSize2 = new TextEncoder().encode(jsonString2).length;

      console.log("Размер JSON в байтах:", byteSize);
      console.log(jsonString);
      console.log("Размер JSON с аниме в байтах:", byteSize2);
      console.log(jsonString2);
      // try{
      //   const response = await axios.put(`/anime/${this.anime.id}`, requestData);
      //   console.log('Anime успешно сохранен: ', response.data);
      // }
      // catch (error) {
      //   console.error('Ошибка при сохранении Anime: ', error);
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
  margin: 2px 0;
}
.mrl5{
  margin-left: 5px;
}
.mr10-0{
  margin: 10px 0;
}
.span-tag {
  margin-top: 5px;
  margin-left: 5px;
  cursor: pointer;
  display: inline-block;
  padding: 0 5px;
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