<template>
  <VerticalBar v-bind:loggedUser="loggedUser" v-bind:page="page" @change-page="changePage" />
  <div class="container">
    <div class="container-content">
      <div v-if="page === 'home'">
        <HomeView v-bind:loggedUser="loggedUser" />
      </div>
      <div v-else-if="page === 'search'">
        <SearchView />
      </div>
      <div v-else-if="page === 'my-repetition'">
        <MyRepetition />
      </div>
    </div>
  </div>


  <button v-on:click="login">LOGIN</button>
  <button v-on:click="getUserRepetitions">CIAO</button>

</template>

<script>
import $ from 'jquery'
import VerticalBar from './components/VerticalBar.vue'
import HomeView from './view/HomeView.vue'
import SearchView from './view/SearchView.vue'
import MyRepetition from './view/MyRepetition.vue'

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
    MyRepetition
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
    },
    getUserRepetitions() {
      $.ajax(process.env.VUE_APP_BASE_URL + "RepetitionServlet", {
        method: "GET",
        data: {
          operation: "selectByEmail",
          email: "matteodedonno@gmail.com",
        },
        xhrFields: {
          withCredentials: true
        },
        crossDomain: true,
        success: (data) => {
          console.log(data)
        }
      })
    }
  },
  mounted() {
  }
}
</script>
