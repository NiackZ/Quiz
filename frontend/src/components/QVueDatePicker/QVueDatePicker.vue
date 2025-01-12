<template>
  <VueDatePicker :model-value="date"
                 :placeholder="placeholder"
                 :class="calcClasses"
                 locale="ru"
                 range
                 model-auto
                 dark
                 hide-input-icon
                 :enable-time-picker="false"
                 :format="vueDatePicker.format"
                 :cancel-text="vueDatePicker.cancelText"
                 :select-text="vueDatePicker.selectText"
                 @update:model-value="$emit('update:date', $event)"
  >
    <template #action-row="{ internalModelValue, selectDate }">
      <div class="action-row">
        <span v-if="internalModelValue && internalModelValue[0]" class="current-selection">
          с {{ internalModelValue[0].toLocaleDateString() + (internalModelValue[1] ? ` по ${internalModelValue[1].toLocaleDateString()}` : '')}}
        </span>
        <v-btn class="my-2" color="primary" dark @click="selectDate" variant="tonal" v-text="'Выбрать'"></v-btn>
      </div>
    </template>
  </VueDatePicker>
  <v-messages :active="true"
              :messages="errorMessages"
              color="rgb(var(--v-theme-error))"
              class="opacity-0 mb-1"
  />
</template>

<script>
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

export default {
  name: "QVueDatePicker",
  components: {VueDatePicker},
  props: {
    date: Object,
    placeholder: {
      type: String,
      default: "Выберите дату"
    },
    errorMessages: {
      type: Array,
      default: []
    }
  },
  data() {
    return {
      vueDatePicker: {
        format: 'dd.MM.yyyy',
        cancelText: "Закрыть",
        selectText: "Выбрать"
      }
    }
  },
  computed: {
    calcClasses() {
      const classes = ['mb-2'];
      if (this.errorMessages.length !== 0) {
        classes.push("error")
      }
      return classes.join(" ")
    }
  }
}
</script>

<style>
.error .dp__input {
  border-color: rgb(var(--v-theme-error));
}
.opacity-0 {
  opacity: 1;
}
.action-row {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
</style>