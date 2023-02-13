<template>
  <div class="file-upload">
    <input type="file" multiple>
  </div>
</template>

<script>
export default {
  name: 'v-file-upload'
}
</script>

<style scoped>
.file-upload{
  padding: 5px;
}
.file-upload input[type=file]{
  border: 1px solid;
  border-radius: 5px;
}
</style>