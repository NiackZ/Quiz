<template>
  <div class="navbar">
    <div>Vue 3</div>
    <div class="navbar_bnts">
      <my-button v-for="route in routes"
                 style="margin-left: 15px"
                 @click="$router.push(route.path)"
      >{{route.title}}</my-button>
    </div>
  </div>
</template>

<script>
import router from "@/router/router";

export default {
  name: 'VNavbar',
  props: {
    routes: {
      type: Array,
      default: () => {
        return [
            {
              path: "",
              title: ""
            }
        ]
      },
      required: true
    }
  },
  methods: {
    router() {
      return router
    }
  }
}
</script>

<style scoped>
.navbar {
  height: 50px;
  background: deepskyblue;
  box-shadow: 2px 2px 4px gray;
  display: flex;
  align-items: center;
  padding: 15px;
}
.navbar_bnts{
  margin-left: auto;
}
</style>