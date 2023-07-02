<template>
  {{vueDatePicker.date}}
  <VueDatePicker :model-value="date"
                 :placeholder="placeholder"
                 class="mb-3"
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
        <v-btn
            class="my-2"
            color="primary"
            dark
            @click="selectDate"
            variant="tonal"
        >Выбрать</v-btn>
      </div>
    </template>
  </VueDatePicker>
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
  }
}
</script>

<style>
.action-row {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
</style>