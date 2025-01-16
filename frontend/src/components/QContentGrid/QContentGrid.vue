<template>
  <v-row>
    <v-col v-for="(element, index) in elements"
           :key="index"
           :cols="computedColumnCount"
           :style="{ height: this.cellHeight }"
    >
      <router-link :to="`/${path}/${element.id}`" class="text-decoration-none">
        <div class="d-flex justify-center grid-cell bg-img-center h-100 "
             :style="{
                width: cellWidth,
                'background-image': `url(${element.posterURL})`
              }"
        >
<!--          TODO Доделать отображение при наведении-->
          <div class="short-description">
            element #{{ element.id }}
          </div>
        </div>
      </router-link>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'QContentGrid',
  props: {
    elements: {
      type: Array,
      default: []
    },
    path: {
      type: String
    },
    cellWidth: {
      type: String,
      default: 'auto'
    },
    cellHeight: {
      type: String,
      default: '500px'
    }
  },
  data() {
    return {
      columnSize: 3
    }
  },
  computed: {
    computedColumnCount() {
      if (this.$vuetify.display.lgAndUp) { // если экран большой
        return 3;
      }
      else if (this.$vuetify.display.md || this.$vuetify.display.sm) { // если экран средний
        return 4;
      }
      else if (this.$vuetify.display.xs) {
        return 6;
      }
    }
  }
}
</script>

<style>

</style>