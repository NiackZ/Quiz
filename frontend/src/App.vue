<template>
  <MyHeader/>
  <input type="text" name="msgText" v-model="msg">
  <div>
    {{msg}}
  </div>
</template>

<script>
import MyHeader from "@/components/header/MyHeader";
export default {
  components: {MyHeader},
  data() {
    return {
      msg: "Hello"
    }
  }
}
</script>

<style>
*{
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
</style>