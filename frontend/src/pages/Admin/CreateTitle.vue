<template>
  <div class="grid grid-cols-12 auto-cols-max">
    <div class="level_2 lg:col-start-2 md:col-start-2 lg:col-span-3 md:col-span-4 col-span-6 grid-flow-row">
      <v-file-upload/>
    </div>
    <div class="level_3 lg:col-span-7 md:col-span-6 col-span-6">2</div>
  </div>
</template>

<script>
import VFileUpload from "@/components/UI/VFileUpload.vue";

export default {
  components: {VFileUpload}

}
</script>

<style scoped>
.level_2{
  background-color: rgb(85, 94, 112);
}
.level_3{
  background-color: rgb(23, 55, 110);
  display: flex;
  flex-grow: 1;
}
</style>