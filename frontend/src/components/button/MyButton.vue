<template>
  <button>
    {{text}}
  </button>
</template>

<script>
export default {
  props:{
    text:{
      type: String,
      require: true
    }
  }
}
</script>

<style scoped>

</style>