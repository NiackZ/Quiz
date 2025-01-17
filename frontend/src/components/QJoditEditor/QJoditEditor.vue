<template>
<!--  TODO поправить прозрачный фон ссылок-->
  <jodit-editor :config="config"
                v-model="desc"
                @change="$emit('update:text', $event.target.value)"
  />
</template>

<script>
import { JoditEditor } from 'jodit-vue'
import 'jodit/build/jodit.es2018.min.css';

export default {
  name: "QJoditEditor",
  components: { JoditEditor },
  props: {
    text: String,
    config: {
      type: Object,
      default: () => ({
        zIndex: 10,
        language: "ru",
        width: "auto",
        minHeight: 200,
        maxHeight: 500,
        toolbarStickyOffset: 100,
        saveModeInCookie: true,
        theme: 'custom'
      })
    }
  },
  watch: {
    text(newText) {
      // Обновляем свойство desc при изменении пропса text
      this.desc = newText;
    },
  },
  data() {
    return {
      desc: ""
    }
  }
}
</script>

<style>
.jodit_theme_custom {
  --jd-color-background-default: transparent;
  --jd-color-border: black;
  --jd-color-panel: rgb(var(--v-theme-surface));
  --jd-color-icon: rgb(var(--v-theme-primary)) !important;
}
.jodit-container:not(.jodit_inline) {
  background-color: transparent;
}
.jodit-status-bar, .jodit-status-bar a.jodit-status-bar-link {
  color: var(--v-theme-on-background) !important;
}
</style>