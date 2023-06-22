<template>
  <v-row>
    <v-col v-for="(element, index) in elements"
           :key="index"
           :cols="computedColumnCount"
           :style="{ height: this.cellHeight }"
    >
      <router-link :to="'/item/' + element.id" class="text-decoration-none">
        <div class="d-flex justify-center grid-cell bg-img-center h-100 "
             :style="{
                width: cellWidth,
                'background-image': 'url(../src/img/poster/darling.jpg)'
              }"
        >
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
      } else if (this.$vuetify.display.smAndUp) { // если экран средний
        return 4;
      }
      return 6;
    }
  }
}
</script>

<style>
.grid-cell {
  border: 0px solid rgba(var(--v-theme-on-background));
  border-radius: 4px;
}
.bg-img-center{
  background-size: cover;
  background-position: center bottom;
  background-repeat: no-repeat;
}
.short-description{
  padding: 15px !important;
  height: 100%;
  width: 100%;
  opacity: 0;
  transition: 0.3s;
  text-align: center;
}
.grid-cell:hover .short-description{
  opacity: 1;
  background-color: rgba(0,0,0,.5);
}
</style>