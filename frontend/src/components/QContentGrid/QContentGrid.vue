<template>
  <v-row>
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
        {{ row }} - {{ column }}
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
    cellWidth: String,
    cellHeight: String
  }
}
</script>

<style scoped>
.grid-cell {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid black;
  height: 100%;
}
</style>