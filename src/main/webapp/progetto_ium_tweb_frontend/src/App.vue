<template>
  <VerticalBar v-bind:loggedUser="loggedUser" v-bind:page="page" @change-page="changePage" />
  <div class="container">
    <div class="container-content">
      <div v-if="page === 'home'">
        <HomeView v-bind:loggedUser="loggedUser" />
      </div>
      <div v-else-if="page === 'search'">
        <SearchView v-bind:loggedUser="loggedUser" />
      </div>
      <div v-else-if="page === 'my-repetitions'">
        <MyRepetitions v-bind:loggedUserEmail="loggedUser.email" />
      </div>
    </div>
  </div>


  <button v-on:click="login">LOGIN</button>

</template>

<script>
import $ from 'jquery'
import VerticalBar from './components/VerticalBar.vue'
import HomeView from './view/HomeView.vue'
import SearchView from './view/SearchView.vue'
import MyRepetitions from './view/MyRepetitions.vue'

export default {
  name: 'App',
  data() {
    return {
      page: "home",
      loggedUser: null,
      userRepetitions: []
    }
  },
  components: {
    VerticalBar,
    HomeView,
    SearchView,
    MyRepetitions
  },
  methods: {
    changePage(page) {
      this.page = page
    },
    login() {
      let self = this
      $.ajax(process.env.VUE_APP_BASE_URL + "UserServlet", {
        method: "POST",
        data: {
          operation: "login",
          email: "matteodedonno@gmail.com",
          password: "matteo"
        },
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success: (data) => {
          console.log(data)
          self.loggedUser = data
        }
      })
    }
  }
}
</script>
