<template>
  <VerticalBar v-bind:page="page" @change-page="changePage" />
  <div class="container">
    <div class="content">
      <div v-if="page === 'home'">
        <HomeView v-bind:loggedUser="loggedUser" />
      </div>
      <div v-else-if="page === 'search'">
        <SearchView />
      </div>
    </div>
  </div>


  <!-- <button v-on:click="login">LOGIN</button> -->

</template>

<script>
import $ from 'jquery'
import VerticalBar from './components/VerticalBar.vue'
import HomeView from './view/HomeView.vue'
import SearchView from './view/SearchView.vue'

export default {
  name: 'App',
  data() {
    return {
      page: "home",
      loggedUser: null
    }
  },
  components: {
    VerticalBar,
    HomeView,
    SearchView
  },
  methods: {
    changePage(page) {
      this.page = page
    },
    login() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "/UserServlet", {
        method: "POST",
        data: {
          operation: "login",
          email: "giuseppecolazzo@gmail.com",
          password: "ciao"
        },
        success: (data) => {
          self.loggedUser = data
        }
      })
    }
  },
  mounted() {
  }
}
</script>
