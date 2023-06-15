<template>
  <v-row v-for="rowIndex in rowCount" :key="rowIndex">
    <v-col
        v-for="column in columnCount"
        :key="column"
        :cols="12 / columnCount"
        :style="{ height: cellHeight }"
    >
      <div
          class="grid-cell"
          :style="{ width: cellWidth }"
      >
        {{ rowIndex }} - {{ column }}
      </div>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'QContentGrid',
  props: {
    rowCount: Number,
    columnCount: Number,
    cellWidth: {
      type: String,
      default: 'auto'
    },
    cellHeight: {
      type: String,
      default: '500px'
    }
  }
}
</script>

<style scoped>
.grid-cell {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid rgba(var(--v-theme-on-background));
  border-radius: 4px;
  height: 100%;
}
</style>