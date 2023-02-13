<template>
  <div class="app">
    <router-view/>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      posts: [],
      title: "",
      body: "",
      show: false,
      postLoading: true,
      selectedSort: "",
      page: 1,
      limit: 10,
      totalPage: 0,
      options: [
        { value: 'title', name: "По названию" },
        { value: 'body', name: "По содержимому" }
      ],
      searchQuery: "",
    }
  },
  methods:{
    changePage(pageNum) {
      this.page = pageNum;
    },
    createPost(post) {
      this.posts.push(post);
      this.show = false;
    },
    removePost(post) {
      this.posts = this.posts.filter(item => item.id !== post.id)
    },
    async fetchPosts(){
      try {
        this.postLoading = true;
        const response = await axios.get('https://jsonplaceholder.typicode.com/posts',{
          params: {
            _page: this.page,
            _limit: this.limit
          }
        });
        this.totalPage = Math.ceil(response.headers['x-total-count'] / this.limit);
        this.posts = response.data;
      }
      catch (e){
        alert(e);
      }
      finally {
        this.postLoading = false;
      }
    }
  },
  mounted() {
    this.fetchPosts()
  },
  watch: {
    // instead of computed way
    // selectedSort(newVal) {
    //   this.posts.sort((p1, p2) => {
    //     return p1[newVal]?.localeCompare(p2[newVal])
    //   });
    // },
    'show': function(newVal) {
      console.log(newVal)
    },
    'page': function(){
      this.fetchPosts();
    }

  },
  computed: {
    sortedPosts() {
      return [...this.posts].sort((p1, p2) => p1[this.selectedSort]?.localeCompare(p2[this.selectedSort]))
    },
    sortedAndSearchedPosts() {
      return this.sortedPosts.filter(post => post.title.includes(this.searchQuery))
    }
  }
}
</script>

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}
body{
  background-color: rgb(40 44 52);
  color: white;
}
form {
  display: flex;
  flex-direction: column;
}
</style>